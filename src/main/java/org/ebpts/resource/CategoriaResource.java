package org.ebpts.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.CategoriaRequestDTO;
import org.ebpts.dto.response.CategoriaResponseDTO;
import org.ebpts.service.CategoriaService;

import java.util.List;

@Path("/api/v1/categoria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    @GET
    public List<CategoriaResponseDTO> listar() {
        return service.getAllCategoria();
    }

    @GET
    @Path("{id}")
    public CategoriaResponseDTO ObtenerById(Long id) {
        return service.obtenerCategoriaById(id);
    }

    @POST
    public Response crear(CategoriaRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.createCategoria(dto))
                .build();
    }

    @PUT
    @Path("{id}")
    public CategoriaResponseDTO actualizar(@PathParam("id") Long id, CategoriaRequestDTO dto) {
        return service.updateCategory(id, dto);
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        service.deleteCategory(id);
        return Response.noContent().build();
    }

}
