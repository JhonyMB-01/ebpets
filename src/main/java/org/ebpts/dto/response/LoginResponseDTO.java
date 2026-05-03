package org.ebpts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    public String token;
    public String username;
    public String rol;

}