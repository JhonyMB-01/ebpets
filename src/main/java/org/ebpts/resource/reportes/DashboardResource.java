package org.ebpts.resource.reportes;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.ebpts.dto.record.DashboardResponse;
import org.ebpts.service.reporte.DashboardService;

@Path("/api/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    DashboardService service;

    @GET
    public DashboardResponse dashboard() {
        return service.obtenerDashboard();
    }
}
