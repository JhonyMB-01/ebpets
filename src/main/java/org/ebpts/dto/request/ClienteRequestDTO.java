package org.ebpts.dto.request;

import lombok.Data;

@Data
public class ClienteRequestDTO {
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
}
