package org.ebpts.service.reporte;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ebpts.dto.record.DashboardKpiDTO;
import org.ebpts.dto.record.DashboardResponse;
import org.ebpts.dto.record.ProductoStockDTO;
import org.ebpts.dto.record.VentaRecienteDTO;
import org.ebpts.repository.DashboardRepository;

import java.util.List;

@ApplicationScoped
public class DashboardService {

    @Inject
    DashboardRepository repository;

    public DashboardResponse obtenerDashboard() {

        DashboardKpiDTO kpis = repository.obtenerDashboard();

        List<ProductoStockDTO> sinStock =
                repository.obtenerProductosSinStock();

        List<VentaRecienteDTO> ultimasVentas =
                repository.obtenerUltimasVentas(3);

        return new DashboardResponse(
                kpis,
                sinStock,
                ultimasVentas

        );

    }
}
