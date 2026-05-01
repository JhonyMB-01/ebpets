package org.ebpts.mapper;

import org.ebpts.dto.ProductoDto;
import org.ebpts.entity.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ProductoMapper {

    @Mapping(source = "precioVenta", target = "precio")
    ProductoDto toDTO(ProductoEntity entity);
}
