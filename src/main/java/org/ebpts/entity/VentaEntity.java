package org.ebpts.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.ws.rs.ClientErrorException;
import lombok.*;
import org.ebpts.utils.EstadoVenta;
import org.ebpts.utils.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    public ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public UsuarioEntity usuario;

    public BigDecimal subtotal;
    public BigDecimal igv;
    public BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    public MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    public EstadoVenta estado;

    public LocalDateTime fecha = LocalDateTime.now();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    public List<DetalleVentaEntity> detalles;

}
