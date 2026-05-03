package org.ebpts.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.ebpts.dto.request.MarcaRequestDTO;
import org.ebpts.dto.response.MarcaResponseDTO;
import org.ebpts.entity.MarcaEntity;
import org.ebpts.mapper.MarcaMapper;
import org.ebpts.repository.MarcaRepository;
import org.ebpts.service.MarcaService;

import java.util.List;

import static org.ebpts.utils.Constant.MARCA_NOT_FOUND;

@ApplicationScoped
public class MarcaServiceImpl  implements MarcaService {

    @Inject
    MarcaMapper marcaMapper;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    public List<MarcaResponseDTO> getAllMarca() {
        return marcaMapper.toDTOList(marcaRepository.listAll());
    }

    @Override
    @Transactional
    public MarcaResponseDTO saveMarca(MarcaRequestDTO requestDTO) {
        MarcaEntity marcaEntity = marcaMapper.toEntity(requestDTO);
        marcaRepository.persist(marcaEntity);
        return marcaMapper.toDTO(marcaEntity);
    }

    @Override
    @Transactional
    public MarcaResponseDTO updateMarca(Long id, MarcaRequestDTO marcaRequestDTO) {
        MarcaEntity marcaEntity = marcaRepository.findById(id);
        if (marcaEntity == null) {
            throw new NotFoundException(MARCA_NOT_FOUND);
        }
        marcaEntity.setNombre(marcaRequestDTO.getNombre());
        return marcaMapper.toDTO(marcaEntity);
    }

    @Override
    @Transactional
    public void deleteMarca(Long id) {
        if (!marcaRepository.deleteById(id)) {
            throw new NotFoundException(MARCA_NOT_FOUND);
        }
    }
}
