package org.ebpts.mapper.reporte;

import org.ebpts.dto.reporte.ReporteVentaDTO;
import org.ebpts.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ReporteVentaMapper {

    @Mapping(source = "cliente.nombre", target = "cliente")
    @Mapping(source = "usuario.username", target = "vendedor")
    @Mapping(source = "id", target = "idVenta")
    ReporteVentaDTO toDTO(VentaEntity venta);

    List<ReporteVentaDTO> toDTOList(List<VentaEntity> ventas);
}
