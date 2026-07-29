package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ebpts.dto.request.ClienteRequestDTO;
import org.ebpts.dto.response.ClienteResponseDTO;
import org.ebpts.entity.ClienteEntity;
import org.ebpts.exception.NotFoundException;
import org.ebpts.mapper.ClienteMapper;
import org.ebpts.repository.ClienteRepository;
import org.ebpts.service.ClienteService;

import java.util.List;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository repository;
    @Inject
    ClienteMapper mapper;


    @Override
    public List<ClienteResponseDTO> getAllCliente() {
        return mapper.toDTOList(repository.listAll());
    }

    @Transactional
    @Override
    public ClienteResponseDTO saveCliente(ClienteRequestDTO requestDTO) {
        ClienteEntity entity = mapper.toEntity(requestDTO);
        repository.persist(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    @Override
    public ClienteResponseDTO updateCliente(Long idCliente, ClienteRequestDTO requestDTO) {
        ClienteEntity clienteEntity = repository.findById(idCliente);
        if (clienteEntity == null) {
            throw new NotFoundException("Cliente no encontrado");
        }
        clienteEntity.setDocumento(requestDTO.getDocumento());
        clienteEntity.setNombre(requestDTO.getNombre());
        clienteEntity.setTelefono(requestDTO.getTelefono());
        clienteEntity.setEmail(requestDTO.getEmail());
        return mapper.toDTO(clienteEntity);
    }

    @Override
    public ClienteResponseDTO getClienteById(Long idCliente) {
        ClienteEntity entity = repository.findById(idCliente);
        return mapper.toDTO(entity);
    }

    @Transactional
    @Override
    public void deleteCliente(Long idCliente) {
        if (!repository.deleteById(idCliente)) {
            throw new NotFoundException("Cliente no encontrado");
        }
    }

}
