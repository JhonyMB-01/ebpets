package org.ebpts.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoEntity extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String codigo;

    @Column(name = "nombre", nullable = false)
    public String nombre;

    @Column(name = "precio_venta", nullable = false)
    public Double precioVenta;

    @Column(name = "afecta_igv", nullable = false)
    public Boolean afectaIgv;
}
