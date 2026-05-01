package org.ebpts.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.ebpts.dto.ProductoDto;
import org.ebpts.service.ProductoService;

import java.util.List;

@Path("/api/v1/producto")
@Produces("application/json")
@Consumes("application/json")
public class ProductoResource {

    @Inject
    ProductoService service;

    @GET
    public List<ProductoDto> getAllProductos() {
        return service.getAllProductos();
    }
}
