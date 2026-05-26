package org.ebpts.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompraDetalleResponseDTO {

    public Long id;
    public String proveedor;
    public LocalDateTime fecha;
    public BigDecimal total;

    public List<DetalleItemResponse> items;

}
