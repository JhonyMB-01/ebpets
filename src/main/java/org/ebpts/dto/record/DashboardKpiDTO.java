package org.ebpts.dto.record;

import java.math.BigDecimal;

public record DashboardKpiDTO(
    BigDecimal ventasHoy,
    BigDecimal ventasMes,
    BigDecimal comprasMes,
    Integer clientes,
    Integer productos,
    Integer stockBajo,
    Integer sinStock,
    Integer ventasHoyCantidad,
    BigDecimal ticketPromedio,
    Integer productosPorVencer
) {}

