package org.ebpts.mapper;

import org.ebpts.dto.response.VentaResponseDTO;
import org.ebpts.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface VentaMapper {

    @Mapping(source = "cliente.nombre", target = "cliente")
    @Mapping(source = "usuario.username", target = "vendedor")
    VentaResponseDTO toDTO(VentaEntity entity);

    List<VentaResponseDTO> toDTOList(List<VentaEntity> list);

}
