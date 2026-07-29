package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.ClienteRequestDTO;
import org.ebpts.service.ClienteService;

@Path("/api/v1/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @GET
    public Response listarClientes() {
        var lista = service.getAllCliente();
        if (lista == null || lista.isEmpty()) {
            return Response.noContent().build(); // 204 No Content
        }
        return Response.ok(lista).build();
    }

    @GET
    @Path("{id}")
    public Response getClienteById(@PathParam("id") Long id) {
        var cliente = service.getClienteById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 Not Found
        }
        return Response.ok(cliente).build();
    }

    @POST
    public Response crearCliente(ClienteRequestDTO dto) {
        var cliente = service.saveCliente(dto);
        return Response.status(Response.Status.CREATED).entity(cliente).build(); // 201 Created
    }

    @PUT
    @Path("{id}")
    public Response actualizarCliente(@PathParam("id") Long id, ClienteRequestDTO dto) {
        var cliente = service.updateCliente(id, dto);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 Not Found
        }
        return Response.ok(cliente).build();
    }

}
