package it.unimib.finalproject.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.unimib.finalproject.server.DB.DBFacade;
import it.unimib.finalproject.server.DB.QueryList;
import it.unimib.finalproject.server.DB.QueryResolution;
import it.unimib.finalproject.server.DB.ScriptResolution;
import it.unimib.finalproject.server.resources.primitives.MovieSession;
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
        ObjectMapper mapper = new ObjectMapper();
        // creating Script sequence
        QueryList script = new QueryList();
        // append the first query
        script.add("GET moviesession:*");
        QueryResolution mss = this.db.query(script).get(0);

        if(!mss.isError()){
            ArrayNode arr = (ArrayNode) mapper.readTree(mss.message());
            script = new QueryList();
            for(JsonNode ms : arr)
                script.add("GET moviesession:" + ms.get("id") + ":seats");
            ScriptResolution totalSeats = this.db.query(script);
            if(totalSeats.containsError())
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .build();

            ArrayNode completeMSS = mapper.createArrayNode();
            for (int i = 0; i < arr.size(); i++) {
                completeMSS.add(
                        appendSeatsToMovieSession(
                                mapper.writeValueAsString(arr.get(i)),
                                totalSeats.get(i).message()
                        )
                );
            }
            return Response
                    .ok(completeMSS)
                    .build();

        }else
            return Response
                    .ok(mss.message())
                    .build();
    }

    // GET /moviesessions/{id}
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtomicMovieSession(@PathParam("id") String id) throws IOException {
        QueryList script = new QueryList();
        script.add("GET moviesession:" + id);
        script.add("GET moviesession:" + id + ":seats");
        ScriptResolution res = this.db.query(script);
        if(!res.containsError()){
            JsonNode completeMS = appendSeatsToMovieSession(res.get(0).message(), res.get(1).message());
            return Response
                    .ok(completeMS)
                    .build();
        }else
            return Response
                    .status(res.error())
                    .build();
    }

    private JsonNode appendSeatsToMovieSession(String moviesessionUnparsed, String seatsUnparsed) throws JsonProcessingException {
        System.out.println(moviesessionUnparsed);
        System.out.println(seatsUnparsed);
        ObjectMapper mapper = new ObjectMapper();
        MovieSession ms = mapper.readValue(moviesessionUnparsed, MovieSession.class);
        Integer[] seats = mapper.readValue(seatsUnparsed, Integer[].class);
        ms.appendSeats(seats);
        return mapper.valueToTree(ms);
    }

}