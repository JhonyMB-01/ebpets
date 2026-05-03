-- =========================================
-- BASE DE DATOS
-- =========================================
CREATE DATABASE EBPETS;
USE EBPETS;

DROP database EBPETS;

-- =========================================
-- TABLAS BASE
-- =========================================

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE marcas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- =========================================
-- USUARIOS
-- =========================================

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150),
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255),
    id_rol INT,
    FOREIGN KEY (id_rol) REFERENCES roles(id)
);

-- =========================================
-- CLIENTES / PROVEEDORES
-- =========================================

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    documento VARCHAR(20),
    telefono VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE proveedores (	
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    ruc varchar(20) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

-- =========================================
-- PRODUCTOS
-- =========================================

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    id_categoria INT,
    id_marca INT,
    precio_venta DECIMAL(10,2) NOT NULL,
    afecta_igv BOOLEAN DEFAULT TRUE,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id),
    FOREIGN KEY (id_marca) REFERENCES marcas(id)
);

-- =========================================
-- IMÁGENES DE PRODUCTOS
-- =========================================

CREATE TABLE producto_imagenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    url VARCHAR(255) NOT NULL,
    es_principal BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

-- =========================================
-- INVENTARIO (POR LOTE)
-- =========================================

CREATE TABLE inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    lote VARCHAR(50),
    fecha_vencimiento DATE,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT DEFAULT 0,
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

-- =========================================
-- MOVIMIENTOS INVENTARIO
-- =========================================

CREATE TABLE movimientos_inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    tipo ENUM('ENTRADA','SALIDA','AJUSTE') NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(255),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

-- =========================================
-- COMPRAS
-- =========================================

CREATE TABLE compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id)
);

CREATE TABLE detalle_compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_compra INT,
    id_producto INT,
    cantidad INT,
    precio_compra DECIMAL(10,2),
    lote VARCHAR(50),
    fecha_vencimiento DATE,
    FOREIGN KEY (id_compra) REFERENCES compras(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

-- =========================================
-- VENTAS
-- =========================================

CREATE TABLE ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2),
    igv DECIMAL(10,2),
    total DECIMAL(10,2),
    metodo_pago ENUM('EFECTIVO','YAPE'),
    estado ENUM('PENDIENTE','PAGADO','CANCELADO') DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    id_inventario INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    descuento DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2),
    FOREIGN KEY (id_venta) REFERENCES ventas(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id),
    FOREIGN KEY (id_inventario) REFERENCES inventario(id)
);

-- =========================================
-- ÍNDICES
-- =========================================

CREATE INDEX idx_producto_codigo ON productos(codigo);
CREATE INDEX idx_inventario_producto ON inventario(id_producto);
CREATE INDEX idx_ventas_fecha ON ventas(fecha);

-- =========================================
-- TABLA TEMPORAL (CARRITO)
-- =========================================

CREATE TABLE tmp_detalle_venta (
    id_usuario INT NOT NULL,
    id_producto INT NOT NULL,
    id_inventario INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id),
    FOREIGN KEY (id_inventario) REFERENCES inventario(id)
);

-- =========================================
-- TRIGGERS
-- =========================================


DELIMITER $$

DROP TRIGGER IF EXISTS before_insert_detalle_ventas;
DELIMITER $$

CREATE TRIGGER before_insert_detalle_ventas
BEFORE INSERT ON detalle_ventas
FOR EACH ROW
BEGIN
    DECLARE v_stock INT;
    DECLARE v_fecha_venc DATE;
    DECLARE v_estado VARCHAR(20);

    -- Obtener estado de la venta
    SELECT estado
    INTO v_estado
    FROM ventas
    WHERE id = NEW.id_venta;

    -- Solo validar stock y vencimiento si está PAGADA
    IF v_estado = 'PAGADO' THEN

        SELECT stock, fecha_vencimiento
        INTO v_stock, v_fecha_venc
        FROM inventario
        WHERE id = NEW.id_inventario
        FOR UPDATE;

        IF v_stock < NEW.cantidad THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Stock insuficiente';
        END IF;

        IF v_fecha_venc IS NOT NULL 
           AND v_fecha_venc < CURDATE() THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Producto vencido';
        END IF;

    END IF;
END$$

DELIMITER ;


DROP TRIGGER IF EXISTS after_insert_detalle_ventas;
DELIMITER $$

CREATE TRIGGER after_insert_detalle_ventas
AFTER INSERT ON detalle_ventas
FOR EACH ROW
BEGIN
    DECLARE v_estado VARCHAR(20);

    -- Obtener estado de la venta
    SELECT estado
    INTO v_estado
    FROM ventas
    WHERE id = NEW.id_venta;

    -- Descontar stock solo si está PAGADA
    IF v_estado = 'PAGADO' THEN

        UPDATE inventario
        SET stock = stock - NEW.cantidad
        WHERE id = NEW.id_inventario;

        INSERT INTO movimientos_inventario (
            id_producto,
            id_inventario,
            tipo,
            cantidad,
            motivo
        )
        VALUES (
            NEW.id_producto,
            NEW.id_inventario,
            'SALIDA',
            NEW.cantidad,
            'Venta'
        );

    END IF;
END$$

DELIMITER ;


DROP TRIGGER IF EXISTS after_update_ventas;
DELIMITER $$

CREATE TRIGGER after_update_ventas
AFTER UPDATE ON ventas
FOR EACH ROW
BEGIN
    -- Solo cuando pasa de PENDIENTE a PAGADO
    IF OLD.estado = 'PENDIENTE' AND NEW.estado = 'PAGADO' THEN

        -- Descontar inventario por cada detalle
        UPDATE inventario i
        JOIN detalle_ventas dv ON dv.id_inventario = i.id
        SET i.stock = i.stock - dv.cantidad
        WHERE dv.id_venta = NEW.id;

        -- Registrar movimientos
        INSERT INTO movimientos_inventario (
            id_producto,
            id_inventario,
            tipo,
            cantidad,
            motivo
        )
        SELECT
            dv.id_producto,
            dv.id_inventario,
            'SALIDA',
            dv.cantidad,
            'Venta'
        FROM detalle_ventas dv
        WHERE dv.id_venta = NEW.id;

    END IF;
END$$
DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_insert_detalle_compras
AFTER INSERT ON detalle_compras
FOR EACH ROW
BEGIN
    DECLARE v_id_inventario INT;

    INSERT INTO inventario (
        id_producto,
        lote,
        fecha_vencimiento,
        stock
    )
    VALUES (
        NEW.id_producto,
        NEW.lote,
        NEW.fecha_vencimiento,
        NEW.cantidad
    );

    SET v_id_inventario = LAST_INSERT_ID();

    INSERT INTO movimientos_inventario (
        id_producto,
        id_inventario,
        tipo,
        cantidad,
        motivo
    )
    VALUES (
        NEW.id_producto,
        v_id_inventario,
        'ENTRADA',
        NEW.cantidad,
        'Compra'
    );
END$$


-- =========================================
-- PROCEDIMIENTO VENTA
-- =========================================

-- PROCEDIMIENTO OBSOLETO
-- NO USADO POR LA API QUARKUS

DELIMITER $$
CREATE PROCEDURE registrar_venta (
    IN p_id_cliente INT,
    IN p_id_usuario INT,
    IN p_metodo_pago VARCHAR(20)
)
BEGIN
    DECLARE v_id_venta INT;
    DECLARE v_subtotal DECIMAL(10,2);
    DECLARE v_igv DECIMAL(10,2);
    DECLARE v_total DECIMAL(10,2);

    START TRANSACTION;

    SELECT IFNULL(SUM(total),0)
    INTO v_subtotal
    FROM tmp_detalle_venta
    WHERE id_usuario = p_id_usuario;

    SET v_igv = v_subtotal * 0.18;
    SET v_total = v_subtotal + v_igv;

    INSERT INTO ventas (
        id_cliente,
        id_usuario,
        subtotal,
        igv,
        total,
        metodo_pago,
        estado
    )
    VALUES (
        p_id_cliente,
        p_id_usuario,
        v_subtotal,
        v_igv,
        v_total,
        p_metodo_pago,
        'PAGADO'
    );

    SET v_id_venta = LAST_INSERT_ID();

    INSERT INTO detalle_ventas (
        id_venta,
        id_producto,
        id_inventario,
        cantidad,
        precio_unitario,
        descuento,
        total
    )
    SELECT
        v_id_venta,
        id_producto,
        id_inventario,
        cantidad,
        precio_unitario,
        descuento,
        total
    FROM tmp_detalle_venta
    WHERE id_usuario = p_id_usuario;

    DELETE FROM tmp_detalle_venta
    WHERE id_usuario = p_id_usuario;

    COMMIT;
END$$

DELIMITER ;


-- =========================================
-- INSERTS
-- =========================================

-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES
('Alimentos', 'Comida para mascotas'),
('Juguetes', 'Juguetes para mascotas'),
('Accesorios', 'Accesorios generales'),
('Correas', 'Correas para paseo');

-- Marcas
INSERT INTO marcas (nombre) VALUES
('Pedigree'),
('Whiskas'),
('Kong'),
('Trixie');

-- Roles
INSERT INTO roles (nombre) VALUES
('Administrador'),
('Vendedor'),
('Almacen');

-- Usuarios
INSERT INTO usuarios (nombre, username, password_hash, id_rol) VALUES
('Admin Sistema', 'admin', 'hash_admin', 1),
('Carlos Vendedor', 'cvendedor', 'hash_vendedor', 2),
('Luis Almacen', 'lalmacen', 'hash_almacen', 3);

-- Clientes
INSERT INTO clientes (nombre, documento, telefono, email) VALUES
('Juan Perez', '12345678', '987654321', 'juan@email.com'),
('Maria Lopez', '87654321', '912345678', 'maria@email.com');

-- Proveedores
INSERT INTO proveedores (nombre,ruc, telefono, email) VALUES
('Distribuidora PetFood', '20468929101', '999888777', 'ventas@petfood.com'),
('Importaciones Mascotas', '2087766558', '988777666', 'contacto@importmascotas.com');

-- Productos
INSERT INTO productos (codigo, nombre, id_categoria, id_marca, precio_venta) VALUES
('P001', 'Alimento para perro 10kg', 1, 1, 120.00),
('P002', 'Alimento para gato 5kg', 1, 2, 80.00),
('P003', 'Juguete mordedor', 2, 3, 25.00),
('P004', 'Correa nylon mediana', 4, 4, 35.00);

-- Producto imagenes
INSERT INTO producto_imagenes (id_producto, url, es_principal)
VALUES (1, 'uploads/productos/p001.jpg', TRUE);

-- Inventario
INSERT INTO inventario (id_producto, lote, fecha_vencimiento, stock, stock_minimo) VALUES
(1, 'L001', '2026-12-31', 50, 10),
(2, 'L002', '2026-10-15', 40, 8),
(3, NULL, NULL, 30, 5),
(4, NULL, NULL, 20, 5);

-- Compras
INSERT INTO compras (id_proveedor, total) VALUES
(1, 2000.00),
(2, 800.00);

-- Detalle compras
INSERT INTO detalle_compras (id_compra, id_producto, cantidad, precio_compra, lote, fecha_vencimiento) VALUES
(1, 1, 50, 90.00, 'L001', '2026-12-31'),
(1, 2, 40, 60.00, 'L002', '2026-10-15'),
(2, 3, 30, 15.00, NULL, NULL),
(2, 4, 20, 20.00, NULL, NULL);

-- Ventas
INSERT INTO ventas (id_cliente, id_usuario, subtotal, igv, total, metodo_pago, estado) VALUES
(1, 2, 145.00, 26.10, 171.10, 'EFECTIVO', 'PAGADO'),
(2, 2, 80.00, 14.40, 94.40, 'YAPE', 'PAGADO');

-- Detalle ventas
INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario, descuento, total) VALUES
(1, 1, 1, 120.00, 0, 120.00),
(1, 3, 1, 25.00, 0, 25.00),
(2, 2, 1, 80.00, 0, 80.00);

-- Movimientos inventario
INSERT INTO movimientos_inventario (id_producto, tipo, cantidad, motivo) VALUES
(1, 'ENTRADA', 50, 'Compra inicial'),
(2, 'ENTRADA', 40, 'Compra inicial'),
(3, 'ENTRADA', 30, 'Compra inicial'),
(4, 'ENTRADA', 20, 'Compra inicial'),
(1, 'SALIDA', 1, 'Venta'),
(3, 'SALIDA', 1, 'Venta'),
(2, 'SALIDA', 1, 'Venta');


INSERT INTO tmp_detalle_venta VALUES (1, 1, 2, 120.00, 0, 240.00);
CALL registrar_venta(1, 1, 'EFECTIVO');

select * from roles


