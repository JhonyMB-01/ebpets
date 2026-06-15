package org.ebpts.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventarioResponseDTO {
    private Long id;
    private String lote;
    private LocalDate fechaVencimiento;
    private Integer stock;
    private Integer stockMinimo;
    private String nombreProducto;
}
