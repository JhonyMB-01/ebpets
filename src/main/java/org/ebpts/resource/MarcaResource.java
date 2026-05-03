package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.CategoriaRequestDTO;
import org.ebpts.dto.request.MarcaRequestDTO;
import org.ebpts.dto.response.CategoriaResponseDTO;
import org.ebpts.dto.response.MarcaResponseDTO;
import org.ebpts.service.MarcaService;

import java.util.List;

@Path("/api/v1/marca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    @GET
    public List<MarcaResponseDTO> listar() {
        return service.getAllMarca();
    }

    @POST
    public Response crear(MarcaRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.saveMarca(dto))
                .build();
    }

    @PUT
    @Path("{id}")
    public MarcaResponseDTO actualizar(@PathParam("id") Long id, MarcaRequestDTO dto) {
        return service.updateMarca(id, dto);
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        service.deleteMarca(id);
        return Response.noContent().build();
    }

}
