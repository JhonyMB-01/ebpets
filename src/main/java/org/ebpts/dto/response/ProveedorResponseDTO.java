package org.ebpts.dto.response;

import lombok.Data;

@Data
public class ProveedorResponseDTO {
    private Long id;
    private String nombre;
    private String ruc;
    private String telefono;
    private String email;
}
