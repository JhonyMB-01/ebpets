package org.ebpts.mapper;

import org.ebpts.dto.response.ProductoResponseDTO;
import org.ebpts.entity.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProductoMapper {

    @Mapping(target = "tieneInventario", expression = "java(entity.getInventarios() != null && !entity.getInventarios().isEmpty())")
    ProductoResponseDTO toDTO(ProductoEntity entity);

    @Mapping(target = "tieneInventario", expression = "java(entity.getInventarios() != null && !entity.getInventarios().isEmpty())")
    List<ProductoResponseDTO> toDTOList(List<ProductoEntity> list);

}