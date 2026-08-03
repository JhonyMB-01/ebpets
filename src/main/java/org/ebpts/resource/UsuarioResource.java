package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.UsuarioRequestDTO;
import org.ebpts.dto.response.UsuarioResponseDTO;
import org.ebpts.service.UsuarioService;

import java.util.List;

@Path("/api/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listarUsaurio();
    }

    @POST
    public Response crear(UsuarioRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(usuarioService.saveUsuario(dto))
                .build();
    }

    @PUT
    @Path("{id}/estado")
    public Response cambiarEstado(
            @PathParam("id") Long id,
            @QueryParam("activo") Boolean activo
    ) {
        usuarioService.updateEstado(id, activo);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public UsuarioResponseDTO obtenerPorId(@PathParam("id") Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PUT
    @Path("{id}")
    public UsuarioResponseDTO actualizar(
            @PathParam("id") Long id,
            UsuarioRequestDTO dto
    ) {
        return usuarioService.actualizarUsuario(id, dto);
    }


}
