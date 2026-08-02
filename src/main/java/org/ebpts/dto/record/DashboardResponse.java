package org.ebpts.dto.record;

import java.util.List;

public record DashboardResponse(

        DashboardKpiDTO kpis,

        List<ProductoStockDTO> productosSinStock,

        List<VentaRecienteDTO> ultimasVentas

        //List<ProductoStockDTO> productosStockBajo

) {
}