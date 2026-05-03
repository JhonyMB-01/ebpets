package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.ebpts.dto.request.ProductoRequestDTO;
import org.ebpts.dto.response.ProductoResponseDTO;
import org.ebpts.service.ProductoService;

import java.util.List;

@Path("/api/v1/producto")
@Produces("application/json")
@Consumes("application/json")
//@RolesAllowed("ADMIN")
public class ProductoResource {

    @Inject
    ProductoService service;


    @GET
    public List<ProductoResponseDTO> listar() {
        return service.getAllProductos();
    }

    @POST
    public Response crear(ProductoRequestDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(service.saveProducto(dto))
                .build();
    }

    @PUT
    @Path("{id}")
    public ProductoResponseDTO actualizar(
            @PathParam("id") Long id,
            ProductoRequestDTO dto
    ) {
        return service.updateProducto(id, dto);
    }

    @PUT
    @Path("{id}/estado")
    public Response cambiarEstado(
            @PathParam("id") Long id,
            @QueryParam("activo") Boolean activo
    ) {
        service.updateEstado(id, activo);
        return Response.noContent().build();
    }

}
