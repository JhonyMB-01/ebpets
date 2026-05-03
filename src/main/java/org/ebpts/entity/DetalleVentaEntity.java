package org.ebpts.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    public VentaEntity venta;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    public ProductoEntity producto;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    public InventarioEntity inventario;

    public Integer cantidad;
    @Column(name = "precio_unitario")
    public BigDecimal precioUnitario;
    public BigDecimal descuento;
    public BigDecimal total;
}

