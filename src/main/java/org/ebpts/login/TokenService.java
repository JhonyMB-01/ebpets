package org.ebpts.login;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.ebpts.entity.UsuarioEntity;

import java.util.Set;

@ApplicationScoped
public class TokenService {

    public String generarToken(UsuarioEntity usuario) {

        return Jwt.issuer("ebpets-api")
                .upn(usuario.getUsername())
                .groups(Set.of(usuario.getRol().getNombre()))
                .claim("userId", usuario.getId())
                .expiresIn(3600) // 1 hora
                .sign();
    }
}
