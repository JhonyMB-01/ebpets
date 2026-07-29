package org.ebpts.dto.response;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
}
