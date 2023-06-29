package it.unimib.finalproject.server.resources;

import it.unimib.finalproject.server.DB.DBFacade;
import it.unimib.finalproject.server.DB.QueryList;
import it.unimib.finalproject.server.DB.QueryResolution;
import it.unimib.finalproject.server.utils.MiscellaneousUtilities;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Path("/moviesessions")
public class MovieSessionResource {
    private final DBFacade db;

    public MovieSessionResource(){
        this.db = DBFacade.getInstance();
    }

    // GET /moviesessions
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovieSessions() throws IOException {
        // creating Script sequence
        QueryList script = new QueryList();
        // append the first query
        script.add("GET moviesession:*");
        QueryResolution mss = this.db.query(script).get(0);
        return MiscellaneousUtilities.forwardResponse(mss);
    }

    // GET /moviesessions/{id}
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtomicMovieSession(@PathParam("id") String id) throws IOException {
        QueryList script = new QueryList();
        script.add("GET moviesession:" + id);
        QueryResolution mss = this.db.query(script).get(0);
        return MiscellaneousUtilities.forwardResponse(mss);
    }

}