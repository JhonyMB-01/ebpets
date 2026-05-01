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
}
