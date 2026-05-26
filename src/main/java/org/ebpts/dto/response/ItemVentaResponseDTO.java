package org.ebpts.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ItemVentaResponseDTO {

    public String producto;
    public Integer cantidad;
    public BigDecimal precioUnitario;
    public BigDecimal descuento;
    public BigDecimal total;

    public String lote;
    public LocalDate fechaVencimiento;


}
