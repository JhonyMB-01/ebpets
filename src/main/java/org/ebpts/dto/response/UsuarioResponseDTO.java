package org.ebpts.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    public Long id;
    public String nombre;
    public String username;
    //public String rol;
    public Boolean activo;
    public String email;
    public RolResponseDTO rol;

}
