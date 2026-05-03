package org.ebpts.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRequestDTO {

    private String codigo;
    private String nombre;
    private Long idCategoria;
    private Long idMarca;
    private BigDecimal precioVenta;
    private Boolean afectaIgv;
}

