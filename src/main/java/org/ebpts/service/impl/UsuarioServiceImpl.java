package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ebpts.dto.request.UsuarioRequestDTO;
import org.ebpts.dto.response.UsuarioResponseDTO;
import org.ebpts.entity.RolEntity;
import org.ebpts.entity.UsuarioEntity;
import org.ebpts.exception.NotFoundException;
import org.ebpts.mapper.UsuarioMapper;
import org.ebpts.repository.RolRepository;
import org.ebpts.repository.UsuarioRepository;
import org.ebpts.service.UsuarioService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;
    @Inject
    RolRepository rolRepository;
    @Inject
    UsuarioMapper mapper;

    @Transactional
    @Override
    public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO dto) {

        if (repository.existsByUsername(dto.username)) {
            throw new IllegalArgumentException("El username ya existe");
        }

        RolEntity rol = rolRepository.findById(dto.idRol);
        if (rol == null) {
            throw new NotFoundException("Rol no encontrado");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.nombre = dto.nombre;
        usuario.username = dto.username;
        usuario.passwordHash = BCrypt.hashpw(dto.password, BCrypt.gensalt());
        usuario.rol = rol;

        repository.persist(usuario);

        return mapper.toDTO(usuario);


    }

    @Override
    public List<UsuarioResponseDTO> listarUsaurio() {
        return repository.listAll()
                .stream()
                .map(mapper::toDTO)
                .toList();

    }

    @Override
    @Transactional
    public void updateEstado(Long id, Boolean activo) {
        UsuarioEntity usuario = repository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        usuario.activo = activo;
        repository.persist(usuario);
    }

    @Override
    public UsuarioResponseDTO getUsuarioById(Long id) {
        UsuarioEntity usuario = repository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return mapper.toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        UsuarioEntity usuario = repository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado");
        }

        if (!usuario.username.equals(dto.username) && repository.existsByUsername(dto.username)) {
            throw new IllegalArgumentException("El username ya existe");
        }

        RolEntity rol = rolRepository.findById(dto.idRol);
        if (rol == null) {
            throw new NotFoundException("Rol no encontrado");
        }

        usuario.nombre = dto.nombre;
        usuario.username = dto.username;
        usuario.activo = dto.activo;
        usuario.rol = rol;
        usuario.email = dto.email;

        if (dto.password != null && !dto.password.isEmpty()) {
            usuario.passwordHash = BCrypt.hashpw(dto.password, BCrypt.gensalt());
        }

        repository.persist(usuario);

        return mapper.toDTO(usuario);
    }

    @Override
    public String recuperarContraseña(String username) {
        UsuarioEntity usuario = repository.findByUsername(username);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado");
        }

        // Aquí deberías implementar la lógica para enviar un correo electrónico con un enlace de recuperación de contraseña
        // Por ahora, simplemente devolvemos un mensaje indicando que se ha enviado el correo
        return "Se ha enviado un correo electrónico a " + usuario.getEmail() + " con instrucciones para recuperar la contraseña.";
    }

}
