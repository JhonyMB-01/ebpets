package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ebpts.dto.request.ProveedorRequestDTO;
import org.ebpts.dto.response.ProveedorResponseDTO;
import org.ebpts.entity.ProveedorEntity;
import org.ebpts.exception.NotFoundException;
import org.ebpts.mapper.ProveedorMapper;
import org.ebpts.repository.ProveedorRepository;
import org.ebpts.service.ProveedorService;

import java.util.List;

@ApplicationScoped
public class ProveedorServiceImpl implements ProveedorService {

    @Inject
    ProveedorRepository repository;
    @Inject
    ProveedorMapper mapper;


    @Override
    public List<ProveedorResponseDTO> getAllProveedor() {
        return mapper.toDTOList(repository.listAll());
    }

    @Transactional
    @Override
    public ProveedorResponseDTO saveProveedor(ProveedorRequestDTO requestDTO) {
        ProveedorEntity entity = mapper.toEnty(requestDTO);
        repository.persist(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    @Override
    public ProveedorResponseDTO updateProveedor(Long idProveedor, ProveedorRequestDTO requestDTO) {
        ProveedorEntity proveedorEntity = repository.findById(idProveedor);
        if (proveedorEntity == null) {
            throw new NotFoundException("Proveedor no encontrado");
        }
        proveedorEntity.setNombre(requestDTO.getNombre());
        proveedorEntity.setRuc(requestDTO.getRuc());
        proveedorEntity.setTelefono(requestDTO.getTelefono());
        proveedorEntity.setEmail(requestDTO.getEmail());

        return mapper.toDTO(proveedorEntity);
    }

    @Transactional
    @Override
    public void deleteProveedor(Long idProveedor) {
        if (!repository.deleteById(idProveedor)) {
            throw new NotFoundException("Provvedor no encontrado");
        }
    }

    @Override
    public ProveedorResponseDTO getProveedorById(Long idProveedor) {
        return mapper.toDTO(repository.findById(idProveedor));
    }
}
