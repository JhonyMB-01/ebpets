package org.ebpts.utils;

public class QuerysSql {

    public static final String SQL_DASHBOARD = """
        
        SELECT
        
        /* Ventas de hoy */
        (
            SELECT COALESCE(SUM(v.total),0)
            FROM ventas v
            WHERE DATE(v.fecha)=CURDATE()
        ) AS ventasHoy,
        
        /* Ventas del mes */
        (
            SELECT COALESCE(SUM(v.total),0)
            FROM ventas v
            WHERE YEAR(v.fecha)=YEAR(CURDATE())
              AND MONTH(v.fecha)=MONTH(CURDATE())
        ) AS ventasMes,
        
        /* Compras del mes */
        (
            SELECT COALESCE(SUM(c.total),0)
            FROM compras c
            WHERE YEAR(c.fecha)=YEAR(CURDATE())
              AND MONTH(c.fecha)=MONTH(CURDATE())
        ) AS comprasMes,
        
        /* Clientes */
        (
            SELECT COUNT(*)
            FROM clientes
        ) AS clientes,
        
        /* Productos */
        (
            SELECT COUNT(*)
            FROM productos
        ) AS productos,
        
        /* Stock bajo */
        (
            SELECT COUNT(*)
            FROM inventario
            WHERE stock <= stock_minimo
              AND stock > 0
        ) AS stockBajo,
        
        /* Sin stock */
        (
            SELECT COUNT(*)
            FROM inventario
            WHERE stock = 0
        ) AS sinStock,
        
        /* Número de ventas de hoy */
        (
            SELECT COUNT(*)
            FROM ventas
            WHERE DATE(fecha)=CURDATE()
        ) AS ventasHoyCantidad,
        
        /* Ticket promedio */
        (
            SELECT COALESCE(AVG(total),0)
            FROM ventas
            WHERE DATE(fecha)=CURDATE()
        ) AS ticketPromedio,
        
        /* Productos por vencer en 30 días */
        (
            SELECT COUNT(*)
            FROM inventario
            WHERE fecha_vencimiento
            BETWEEN CURDATE()
                AND DATE_ADD(CURDATE(),INTERVAL 30 DAY)
        ) AS productosPorVencer;
        
        """;

    public static final String SQL_PRODUCTOS_SIN_STOCK = """
            SELECT
                p.id,
                p.codigo,
                p.nombre,
                i.stock,
                i.stock_minimo
            FROM inventario i
            JOIN productos p
                ON p.id=i.id_producto
            WHERE i.stock=0
            ORDER BY p.nombre;
        """;

    public static final String SQL_ULTIMAS_VENTAS = """
            SELECT
                v.id,
                c.nombre,
                v.total,
                v.fecha
            FROM ventas v
            JOIN clientes c
                ON c.id=v.id_cliente
            ORDER BY v.fecha DESC LIMIT 3;
        """;
}
