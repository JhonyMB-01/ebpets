package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.ebpts.dto.response.InventarioResponseDTO;
import org.ebpts.dto.response.ValidacionInventarioDTO;
import org.ebpts.service.InventarioService;

import java.util.List;

@Path("/api/inventarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventarioResource {

    @Inject
    InventarioService service;

    @GET
    @Path("/producto/{idProducto}")
    public List<InventarioResponseDTO> obtenerPorProducto(
            @PathParam("idProducto") Long idProducto) {

        return service.obtenerPorProducto(idProducto);
    }

    @GET
    public List<InventarioResponseDTO> listar() {
        return service.listarTodos();
    }


    @GET
    @Path("/{id}")
    public InventarioResponseDTO obtener(@PathParam("id") Long id) {
        return service.obtenerPorId(id);
    }


    @GET
    @Path("/{id}/validarStock")
    public ValidacionInventarioDTO validar(
            @PathParam("id") Long id,
            @QueryParam("cantidad") Integer cantidad) {

        return service.validarStokById(id, cantidad);
    }





}
