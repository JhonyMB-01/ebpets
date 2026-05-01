package org.ebpts.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        int status = 500;
        String mensaje = "Error interno del servidor";

        if (exception instanceof NotFoundException) {
            status = 404;
            mensaje = exception.getMessage();
        } else if (exception instanceof IllegalArgumentException) {
            status = 400;
            mensaje = exception.getMessage();
        }

        ErrorResponse error = new ErrorResponse(
                mensaje,
                status,
                LocalDateTime.now()
        );

        return Response.status(status).entity(error).build();

    }
}
