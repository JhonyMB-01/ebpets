package org.ebpts.dto.response;

import lombok.Data;
import org.ebpts.utils.EstadoVenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VentaResponseDTO {
    private Long id;
    private String cliente;
    private String vendedor;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private EstadoVenta estado;
    private LocalDateTime fecha;
}