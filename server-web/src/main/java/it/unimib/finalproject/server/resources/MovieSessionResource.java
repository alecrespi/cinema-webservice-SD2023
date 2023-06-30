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

    // GET /moviesessions?movie={id}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovieSessions(@QueryParam("movie") String movieid) throws IOException {
        if(movieid == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        ObjectMapper mapper = new ObjectMapper();
        // getting all moviesessions
        QueryList script = new QueryList();
        script.add("GET moviesession:*");
        QueryResolution mss = this.db.query(script).get(0);

        // checking if there was some error
        if(!mss.isError()){

            // now retrieving all seats information
            ArrayNode filtered = filterByParameter("movie", movieid, mss.message());
            script = new QueryList();
            // append a list of queries, every query targets a single moviesession
            for(JsonNode ms : filtered)
                script.add("GET moviesession:" + ms.get("id") + ":seats");
            ScriptResolution totalSeats = this.db.query(script);

            // if there is an error, it's a db-side error
            if(totalSeats.containsError())
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .build();

            // combines moviesession:* with moviesession:*:seats
            ArrayNode completeMSS = mapper.createArrayNode();
            for (int i = 0; i < filtered.size(); i++) {
                completeMSS.add(
                        appendSeatsToMovieSession(
                                mapper.writeValueAsString(filtered.get(i)),
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

    private JsonNode appendSeatsToMovieSession(String moviesessionUnparsed, String seatsUnparsed)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MovieSession ms = mapper.readValue(moviesessionUnparsed, MovieSession.class);
        Integer[] seats = mapper.readValue(seatsUnparsed, Integer[].class);
        ms.appendSeats(seats);
        return mapper.valueToTree(ms);
    }

    private ArrayNode filterByParameter(String attribute, String param, String unparsed)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode parsed = (ArrayNode) mapper.readTree(unparsed);
        ArrayNode filtered = mapper.createArrayNode();
        for(JsonNode obj : parsed){
            if(obj.get(attribute).asText().equals(param))
                filtered.add(obj);
        }
        return filtered;
    }

}