package org.ebpts.resource;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.RolRequestDTO;
import org.ebpts.dto.response.RolResponseDTO;
import org.ebpts.service.RolSevice;

import java.util.List;

@Path("/api/roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("Administrador")
public class RolResource {

    @Inject
    RolSevice rolSevice;

    @GET
    public List<RolResponseDTO> listar() {
        return rolSevice.getAllRol();
    }

    @POST
    public Response crear(RolRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(rolSevice.saveRol(dto))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        rolSevice.deleteRol(id);
        return Response.noContent().build();
    }

}
