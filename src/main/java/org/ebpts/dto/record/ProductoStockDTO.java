package org.ebpts.dto.record;

public record ProductoStockDTO(
        Long idProducto,
        String codigo,
        String producto,
        Integer stock,
        Integer stockMinimo

) {
}
