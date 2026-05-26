package org.ebpts.mapper;

import org.ebpts.dto.response.CompraDetalleResponseDTO;
import org.ebpts.dto.response.CompraResponseDTO;
import org.ebpts.entity.CompraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta", uses = {DetalleCompraMapper.class})
public interface CompraMapper {

    @Mapping(source = "proveedor.nombre", target = "proveedor")
    CompraResponseDTO toDTO(CompraEntity entity);

    List<CompraResponseDTO> toDTOList(List<CompraEntity> list);

    @Mapping(source = "proveedor.nombre", target = "proveedor")
    @Mapping(source = "detalles", target = "items")
    CompraDetalleResponseDTO toDetailResponse(CompraEntity compra);

}
