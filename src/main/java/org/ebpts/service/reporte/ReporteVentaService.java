package org.ebpts.service.reporte;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ebpts.dto.reporte.ReporteVentaDTO;
import org.ebpts.mapper.reporte.ReporteVentaMapper;
import org.ebpts.repository.VentaRepository;
import org.ebpts.utils.EstadoVenta;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDate;
import java.util.List;

import static org.ebpts.utils.Constant.ROL_VENDEDOR;

@ApplicationScoped
public class ReporteVentaService {

    @Inject
    VentaRepository ventaRepository;
    @Inject
    ReporteVentaMapper mapper;
    @Inject
    JsonWebToken jwt;

    public List<ReporteVentaDTO> ventasPendientes(
            LocalDate desde,
            LocalDate hasta,
            String vendedor
    ) {

        // Si es vendedor, forzar su username
        if (jwt.getGroups().contains(ROL_VENDEDOR)) {
            vendedor = jwt.getName();
        }

        return mapper.toDTOList(
                ventaRepository.listarPorEstado(
                        EstadoVenta.PENDIENTE,
                        desde,
                        hasta,
                        vendedor
                )
        );
    }

    public List<ReporteVentaDTO> ventasPagadas(
            LocalDate desde,
            LocalDate hasta,
            String vendedor
    ) {

        if (jwt.getGroups().contains(ROL_VENDEDOR)) {
            vendedor = jwt.getName();
        }

        return mapper.toDTOList(
                ventaRepository.listarPorEstado(
                        EstadoVenta.PAGADO,
                        desde,
                        hasta,
                        vendedor
                )
        );
    }
}
