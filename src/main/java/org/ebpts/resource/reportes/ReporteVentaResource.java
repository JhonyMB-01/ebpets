package org.ebpts.resource.reportes;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.ebpts.dto.reporte.ReporteVentaDTO;
import org.ebpts.service.reporte.ReporteVentaService;

import java.time.LocalDate;
import java.util.List;

@Path("/api/reportes/ventas")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({ "Administrador", "Vendedor" })
public class ReporteVentaResource {


    @Inject
    ReporteVentaService service;

    @GET
    @Path("/pendientes")
    public List<ReporteVentaDTO> pendientes(
            @QueryParam("desde") LocalDate desde,
            @QueryParam("hasta") LocalDate hasta,
            @QueryParam("vendedor") String vendedor
    ) {
        return service.ventasPendientes(desde, hasta, vendedor);
    }

    @GET
    @Path("/pagadas")
    public List<ReporteVentaDTO> pagadas(
            @QueryParam("desde") LocalDate desde,
            @QueryParam("hasta") LocalDate hasta,
            @QueryParam("vendedor") String vendedor
    ) {
        return service.ventasPagadas(desde, hasta, vendedor);
    }

}
