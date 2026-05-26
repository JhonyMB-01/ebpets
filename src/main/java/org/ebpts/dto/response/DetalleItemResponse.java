package org.ebpts.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DetalleItemResponse {

    public String producto;
    public Integer cantidad;
    public BigDecimal precioCompra;
    public String lote;
    public LocalDate fechaVencimiento;

}
