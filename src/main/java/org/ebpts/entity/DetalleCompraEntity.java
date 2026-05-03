package org.ebpts.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCompraEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "id_compra")
    public CompraEntity compra;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    public ProductoEntity producto;

    public Integer cantidad;

    @Column(name = "precio_compra")
    public BigDecimal precioCompra;

    public String lote;

    @Column(name = "fecha_vencimiento")
    public LocalDate fechaVencimiento;

}
