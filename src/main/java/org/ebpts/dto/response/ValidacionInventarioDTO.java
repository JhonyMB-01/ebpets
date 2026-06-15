package org.ebpts.dto.response;

import lombok.Data;

@Data
public class ValidacionInventarioDTO {

    private boolean valido;
    private Integer stockDisponible;
}
