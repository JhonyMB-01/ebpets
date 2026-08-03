package org.ebpts.dto.request;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    public String nombre;
    public String username;
    public String password;
    public Long idRol;
    public Boolean activo;
    public String email;
}
