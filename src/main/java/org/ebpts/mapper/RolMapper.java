package org.ebpts.mapper;


import org.ebpts.dto.request.RolRequestDTO;
import org.ebpts.dto.response.RolResponseDTO;
import org.ebpts.entity.RolEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface RolMapper {

    RolEntity toEntity(RolRequestDTO dto);

    RolResponseDTO toDTO(RolEntity entity);

    List<RolResponseDTO> toDTOList(List<RolEntity> list);
}

