package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.CompraRequestDTO;
import org.ebpts.dto.response.CompraResponseDTO;
import org.ebpts.service.CompraService;

import java.util.List;

@Path("/api/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@RolesAllowed("ADMIN")
public class CompraResource {

    @Inject
    CompraService service;

    @POST
    public Response registrar(CompraRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.crearCompra(dto))
                .build();
    }

    @GET
    public List<CompraResponseDTO> listar() {
        return service.getAllCompras();
    }

    @GET
    @Path("{id}")
    public CompraResponseDTO obtener(@PathParam("id") Long id) {
        return service.getByIdCompra(id);
    }
}