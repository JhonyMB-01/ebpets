package org.ebpts.mapper;

import org.ebpts.dto.response.InventarioResponseDTO;
import org.ebpts.entity.InventarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface InventarioMapper {


    @Mapping(source = "producto.nombre", target = "nombreProducto")
    InventarioResponseDTO toDTO(InventarioEntity entity);

    @Mapping(source = "producto.nombre", target = "nombreProducto")
    List<InventarioResponseDTO> toDTOList(List<InventarioEntity> entities);

}
