package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ebpts.dto.request.RolRequestDTO;
import org.ebpts.dto.response.RolResponseDTO;
import org.ebpts.entity.RolEntity;
import org.ebpts.exception.NotFoundException;
import org.ebpts.mapper.RolMapper;
import org.ebpts.repository.RolRepository;
import org.ebpts.service.RolSevice;

import java.util.List;

@ApplicationScoped
public class RolServiceImpl implements RolSevice {

    @Inject
    RolRepository repository;
    @Inject
    RolMapper mapper;


    @Override
    public List<RolResponseDTO> getAllRol() {
        return mapper.toDTOList(repository.listAll());
    }

    @Transactional
    @Override
    public RolResponseDTO saveRol(RolRequestDTO rolRequestDTO) {
        RolEntity rol = mapper.toEntity(rolRequestDTO);
        repository.persist(rol);
        return mapper.toDTO(rol);
    }

    @Transactional
    @Override
    public void deleteRol(Long idRol) {
        if (!repository.deleteById(idRol)) {
            throw new NotFoundException("Rol no encontrado");
        }


    }
}
