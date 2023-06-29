package it.unimib.finalproject.server.serverconfig;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class IOExceptionMapper implements ExceptionMapper<IOException> {

    @Override
    public Response toResponse(IOException exception) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
    }
}