package org.ebpts.resource;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.VentaRequestDTO;
import org.ebpts.dto.response.VentaDetalleResponseDTO;
import org.ebpts.dto.response.VentaResponseDTO;
import org.ebpts.service.VentaService;

import java.util.List;

@Path("/api/ventas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({ "Administrador", "Vendedor" })
public class VentaResource {

    @Inject
    VentaService service;

    @POST
    public Response registrar(VentaRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.saveVenta(dto))
                .build();
    }

    @PATCH
    @Path("{id}/confirmar")
    public VentaResponseDTO confirmarVenta(@PathParam("id") Long id) {
        return service.confirmarVenta(id);
    }

    @GET
    public List<VentaResponseDTO> listar(@QueryParam("estado") String estado) {
        return service.getAllVenta();
    }


    @GET
    @Path("/{id}")
    public VentaDetalleResponseDTO obtenerDetalleVentaById(@PathParam("id") Long id) {
        return service.obtenerDetalleVentaById(id);
    }

}

