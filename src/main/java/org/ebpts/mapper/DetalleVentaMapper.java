package org.ebpts.mapper;

import org.ebpts.dto.response.ItemVentaResponseDTO;
import org.ebpts.entity.DetalleVentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface DetalleVentaMapper {

    @Mapping(source = "producto.nombre", target = "producto")
    @Mapping(source = "inventario.lote", target = "lote")
    @Mapping(source = "inventario.fechaVencimiento", target = "fechaVencimiento")
    ItemVentaResponseDTO toItemVentaResponseDTO(DetalleVentaEntity entity);

}
