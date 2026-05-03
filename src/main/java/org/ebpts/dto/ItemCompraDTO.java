package org.ebpts.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ItemCompraDTO {
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioCompra;
    private String lote;
    private LocalDate fechaVencimiento;
}
