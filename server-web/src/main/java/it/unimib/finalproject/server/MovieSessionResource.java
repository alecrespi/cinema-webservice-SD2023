package it.unimib.finalproject.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimib.finalproject.server.DB.DBFacade;
import it.unimib.finalproject.server.DB.QueryList;
import it.unimib.finalproject.server.DB.QueryResolution;
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

    // GET /movieSession
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovieSessions() {
        try{
            ObjectMapper mapper = new ObjectMapper();
            // creating Script sequence
            QueryList script = new QueryList();
            // append the first query
            script.add("GET moviesession:*");
            QueryResolution mss = this.db.query(script).get(0);


            // MEMO PER CRESPI
            // ricordati di fixare il problema della serializzazioen delle stringhe
            if(!mss.isError())
                return Response
                    .ok(mss.message())
                    .build();
            else
                return Response
                    .status(mss.error())
                    .build();
        }catch (IOException e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // GET /movieSession/{idMovieSession}
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtomicMovieSession(@PathParam("id") String id) {
        try{
            QueryList script = new QueryList();
            script.add("GET moviesession:" + id);
            QueryResolution mss = this.db.query(script).get(0);
            System.out.println(mss.message());
            if(!mss.isError())
                return Response
                        .ok(mss.message())
                        .build();
            else
                return Response
                        .status(mss.error())
                        .build();
        }catch (IOException e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}