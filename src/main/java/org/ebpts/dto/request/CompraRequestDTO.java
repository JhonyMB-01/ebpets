package org.ebpts.dto.request;

import lombok.Data;
import org.ebpts.dto.ItemCompraDTO;

import java.util.List;

@Data
public class CompraRequestDTO {
    private Long idProveedor;
    private List<ItemCompraDTO> items;
}
