package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.CategoriaRequestDTO;
import org.ebpts.dto.request.ProveedorRequestDTO;
import org.ebpts.dto.response.CategoriaResponseDTO;
import org.ebpts.dto.response.ProductoResponseDTO;
import org.ebpts.dto.response.ProveedorResponseDTO;
import org.ebpts.service.ProveedorService;

import java.util.List;

@Path("/api/v1/proveedor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProveedorResource {

    @Inject
    ProveedorService service;

    @GET
    public Response listar() {
        List<ProveedorResponseDTO> lista = service.getAllProveedor();
        if (lista == null || lista.isEmpty()) {
            return Response.noContent().build(); // 204 No Content
        }
        return Response.ok(lista).build();
    }

    @GET
    @Path("{id}")
    public ProveedorResponseDTO getProveedorById(@PathParam("id") Long id) {
        return service.getProveedorById(id);
    }

    @POST
    public Response crear(ProveedorRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.saveProveedor(dto))
                .build();
    }


    @PUT
    @Path("{id}")
    public ProveedorResponseDTO actualizar(@PathParam("id") Long id, ProveedorRequestDTO dto) {
        return service.updateProveedor(id, dto);
    }


    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        service.deleteProveedor(id);
        return Response.noContent().build();
    }
}
