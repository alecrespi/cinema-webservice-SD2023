package it.unimib.finalproject.server;

import it.unimib.finalproject.server.DB.DBFacade;
import it.unimib.finalproject.server.DB.QueryList;
import it.unimib.finalproject.server.DB.QueryResolution;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Path("/movies")
public class MovieResource {
    private final DBFacade db;

    public MovieResource(){
        this.db = DBFacade.getInstance();
    }

    // GET /movies
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies() throws IOException {
        QueryList script = new QueryList();
        script.add("GET movie:*");
        QueryResolution mss = this.db.query(script).get(0);
        return forwardResponse(mss);
    }

    // GET /movies/{id}
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtomicMovie(@PathParam("id") String id) throws IOException {
        QueryList script = new QueryList();
        script.add("GET movie:" + id);
        QueryResolution mss = this.db.query(script).get(0);
        return forwardResponse(mss);
    }


    // this method is for better legibility
    private Response forwardResponse(QueryResolution res){
        if(!res.isError())
            return Response
                    .ok(res.message())
                    .build();
        else
            return Response
                    .status(res.error())
                    .build();
    }

}