package org.ebpts.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVentaDTO {
    private Long idProducto;
    private Long idInventario;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
}
