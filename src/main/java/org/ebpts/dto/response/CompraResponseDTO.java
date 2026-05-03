package org.ebpts.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompraResponseDTO {
    private Long id;
    private String proveedor;
    private BigDecimal total;
    private LocalDateTime fecha;
}
