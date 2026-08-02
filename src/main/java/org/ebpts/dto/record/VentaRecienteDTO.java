package org.ebpts.dto.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VentaRecienteDTO(

        Long idVenta,
        String cliente,
        BigDecimal total,
        LocalDateTime fecha

) {
}
