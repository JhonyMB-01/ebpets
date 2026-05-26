package org.ebpts.mapper;

import org.ebpts.dto.response.DetalleItemResponse;
import org.ebpts.entity.DetalleCompraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface DetalleCompraMapper {


    @Mapping(source = "producto.nombre", target = "producto")
    DetalleItemResponse toDto(DetalleCompraEntity entity);



}
