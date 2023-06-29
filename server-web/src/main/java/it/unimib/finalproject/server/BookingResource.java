package it.unimib.finalproject.server;

import jakarta.ws.rs.*;

@Path("/bookings")
public class BookingResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}