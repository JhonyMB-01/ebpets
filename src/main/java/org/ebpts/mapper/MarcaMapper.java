package org.ebpts.mapper;

import org.ebpts.dto.request.MarcaRequestDTO;
import org.ebpts.dto.response.MarcaResponseDTO;
import org.ebpts.entity.MarcaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface MarcaMapper {

    MarcaEntity toEntity(MarcaRequestDTO dto);

    MarcaResponseDTO toDTO(MarcaEntity entity);

    List<MarcaResponseDTO> toDTOList(List<MarcaEntity> list);
}
