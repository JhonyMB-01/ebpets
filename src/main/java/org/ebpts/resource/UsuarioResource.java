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

}
