package org.ebpts.mapper;

import org.ebpts.dto.response.ProductoResponseDTO;
import org.ebpts.entity.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProductoMapper {


    @Mapping(source = "categoria.nombre", target = "categoria")
    @Mapping(source = "marca.nombre", target = "marca")
    ProductoResponseDTO toDTO(ProductoEntity entity);

    List<ProductoResponseDTO> toDTOList(List<ProductoEntity> list);
}
