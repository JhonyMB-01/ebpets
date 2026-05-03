package org.ebpts.login;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ebpts.dto.request.LoginRequestDTO;
import org.ebpts.dto.response.LoginResponseDTO;
import org.ebpts.entity.UsuarioEntity;
import org.ebpts.exception.NotFoundException;
import org.ebpts.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioRepository usuarioRepository;
    @Inject TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        UsuarioEntity usuario = usuarioRepository
                .find("username", dto.username)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (!BCrypt.checkpw(dto.password, usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = tokenService.generarToken(usuario);

        return new LoginResponseDTO(
                token,
                usuario.getUsername(),
                usuario.getRol().getNombre()
        );
    }
}