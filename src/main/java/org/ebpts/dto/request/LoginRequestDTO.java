package org.ebpts.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    public String username;
    public String password;
}