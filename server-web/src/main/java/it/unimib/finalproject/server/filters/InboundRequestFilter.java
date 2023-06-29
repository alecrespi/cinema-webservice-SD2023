package it.unimib.finalproject.server.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InboundRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext req) {
        // for troubleshooting purposes only
        System.out.println("~~> " + req.getMethod() + " /" + req.getUriInfo().getPath());
    }
}