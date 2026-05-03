package org.ebpts.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoResponseDTO {
    public Long id;
    public String codigo;
    public String nombre;
    public String categoria;
    public String marca;
    public BigDecimal precioVenta;
    public Boolean afectaIgv;
    public Boolean activo;
}
