package org.ebpts.mapper;

import org.ebpts.dto.request.CategoriaRequestDTO;
import org.ebpts.dto.response.CategoriaResponseDTO;
import org.ebpts.entity.CategoriaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CategoriaMapper {


    CategoriaEntity toEntity(CategoriaRequestDTO dto);

    CategoriaResponseDTO toDTO(CategoriaEntity entity);

    List<CategoriaResponseDTO> toDTOList(List<CategoriaEntity> list);

}
