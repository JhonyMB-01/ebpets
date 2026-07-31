package org.ebpts.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoResponseDTO {
    public Long id;
    public String codigo;
    public String nombre;
    public BigDecimal precioVenta;
    public Boolean afectaIgv;
    public Boolean activo;
    private MarcaResponseDTO marca;
    private CategoriaResponseDTO categoria;
    private Boolean tieneInventario;
}
