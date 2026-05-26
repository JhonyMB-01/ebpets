package org.ebpts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDetalleResponseDTO {

    public Long id;

    public String cliente;
    public String vendedor;

    public BigDecimal subtotal;
    public BigDecimal igv;
    public BigDecimal total;

    public String estado;
    public LocalDateTime fecha;


    public List<ItemVentaResponseDTO> items;
}
