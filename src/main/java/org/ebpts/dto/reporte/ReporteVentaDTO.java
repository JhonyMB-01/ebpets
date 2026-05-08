package org.ebpts.dto.reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ebpts.utils.EstadoVenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReporteVentaDTO {
    private Long idVenta;
    private String cliente;
    private String vendedor;
    private BigDecimal total;
    private EstadoVenta estado;
    private LocalDateTime fecha;
}
