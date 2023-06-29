package it.unimib.finalproject.server.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OutboundResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) {
        if(res.getStatus() >= 400){
            String message = "{\"message\":\"" + Response.Status.fromStatusCode(res.getStatus()) + "\"}";
            res.setEntity(message);
        }
        System.out.println("<~~ " + res.getStatus() + " " + Response.Status.fromStatusCode(res.getStatus()));
    }
}