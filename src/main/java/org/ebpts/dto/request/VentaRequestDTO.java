package org.ebpts.dto.request;

import lombok.Data;
import org.ebpts.dto.ItemVentaDTO;
import org.ebpts.utils.MetodoPago;

import java.util.List;

@Data
public class VentaRequestDTO {
    public Long idCliente;
    public MetodoPago metodoPago;
    public List<ItemVentaDTO> items;
}
