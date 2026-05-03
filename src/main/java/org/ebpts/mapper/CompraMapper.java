package org.ebpts.mapper;

import org.ebpts.dto.response.CompraResponseDTO;
import org.ebpts.entity.CompraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CompraMapper {

    @Mapping(source = "proveedor.nombre", target = "proveedor")
    CompraResponseDTO toDTO(CompraEntity entity);

    List<CompraResponseDTO> toDTOList(List<CompraEntity> list);
}
