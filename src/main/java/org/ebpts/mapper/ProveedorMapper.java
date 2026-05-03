package org.ebpts.mapper;

import org.ebpts.dto.request.ProveedorRequestDTO;
import org.ebpts.dto.response.ProveedorResponseDTO;
import org.ebpts.entity.ProveedorEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProveedorMapper {

    ProveedorEntity toEnty(ProveedorRequestDTO dto);

    ProveedorResponseDTO toDTO(ProveedorEntity entity);

    List<ProveedorResponseDTO> toDTOList(List<ProveedorEntity> list);


}
