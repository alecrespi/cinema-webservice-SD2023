package it.unimib.finalproject.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimib.finalproject.server.DB.DBFacade;
import it.unimib.finalproject.server.DB.QueryList;
import it.unimib.finalproject.server.DB.QueryResolution;
import it.unimib.finalproject.server.DB.ScriptResolution;
import it.unimib.finalproject.server.resources.primitives.Booking;
import it.unimib.finalproject.server.utils.MiscellaneousUtilities;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/bookings")
public class BookingResource {
    private final DBFacade db;

    public BookingResource(){
        this.db = DBFacade.getInstance();
    }

    // GET /bookings/{code}
    @Path("/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("code") String code) throws IOException {
        QueryList script = new QueryList();
        script.add("GET booking:" + code.toUpperCase());
        QueryResolution mss = this.db.query(script).get(0);
        return MiscellaneousUtilities.forwardResponse(mss);
    }

    // POST /bookings
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Booking content = mapper.readValue(body,Booking.class);
            Integer[] seatsOfMs = getMovieSessionSeats(content.getMoviesession());
            if(seatsOfMs == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            // now checking availability in the moviesession total seats
            if(content.checkAvailability(Arrays.asList(seatsOfMs))){
                List<Integer> combined = combineLists(Arrays.asList(seatsOfMs), content.getSeats());
                // prepare update queries
                QueryList script = new QueryList();
                String msSeatsKey = "moviesession:"+content.getMoviesession()+":seats";
                String bookingKey = "booking:" + content.assignCode();
                script.add("BIND " + msSeatsKey);
                script.add("SET " + bookingKey + " " + mapper.writeValueAsString(content));
                script.add("SET " + msSeatsKey + " " + mapper.writeValueAsString(combined));
                script.add("RELEASE " + msSeatsKey);

                // launch update routine
                ScriptResolution res = this.db.query(script);
                if(res.containsError())
                    return Response.status(res.error()).build();
                return Response.created(null).entity(content).build();
            }else
                return Response
                        .status(Response.Status.CONFLICT)
                        .build();
        }catch(JsonProcessingException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    // PUT /bookings
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editBooking(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Booking content = mapper.readValue(body,Booking.class);
            Booking oldBooking = getOldBooking(content.getCode());
            Integer[] seatsOfMs = getMovieSessionSeats(content.getMoviesession());
            if(seatsOfMs == null || oldBooking == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            List<Integer> othersReservations = listDiff(Arrays.asList(seatsOfMs), oldBooking.getSeats());

            // now checking availability in the moviesession total seats
            if(content.checkAvailability(othersReservations)){
                List<Integer> combined = combineLists(othersReservations, content.getSeats());
                // prepare update queries
                QueryList script = new QueryList();
                String msSeatsKey = "moviesession:"+content.getMoviesession()+":seats";
                String bookingKey = "booking:" + content.getCode();
                script.add("BIND " + bookingKey + " "  + msSeatsKey);
                script.add("SET " + bookingKey + " " + mapper.writeValueAsString(content));
                script.add("SET " + msSeatsKey + " " + mapper.writeValueAsString(combined));
                script.add("RELEASE " + bookingKey + " "  + msSeatsKey);

                // launch update routine
                ScriptResolution res = this.db.query(script);
                if(res.containsError())
                    return Response.status(res.error()).build();
                return Response.noContent().build();
            }else
                return Response
                        .status(Response.Status.CONFLICT)
                        .build();
        }catch(JsonProcessingException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    // DELETE /bookings/{code}
    @Path("/{code}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("code") String code) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Booking oldBooking = getOldBooking(code);
            if(oldBooking == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            Integer[] seatsOfMs = getMovieSessionSeats(oldBooking.getMoviesession());
            List<Integer> othersReservations = listDiff(Arrays.asList(seatsOfMs), oldBooking.getSeats());

            // prepare update queries
            QueryList script = new QueryList();
            String msSeatsKey = "moviesession:" + oldBooking.getMoviesession() + ":seats";
            String bookingKey = "booking:" + code;
            script.add("REMOVE " + bookingKey);
            script.add("SET " + msSeatsKey + " " + mapper.writeValueAsString(othersReservations));

            // launch update routine
            ScriptResolution res = this.db.query(script);
            if(res.containsError())
                return Response.status(res.error()).build();
            return Response
                    .ok()
                    .entity(oldBooking)
                    .build();

        }catch(JsonProcessingException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
//        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    private Integer[] getMovieSessionSeats(int mdId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        QueryList ql = new QueryList("GET moviesession:" + mdId + ":seats");
        QueryResolution res = this.db.query(ql).get(0);
        if(res.isError())
            return null;
        else
            return mapper.readValue(res.message(), Integer[].class);
    }

    private Booking getOldBooking(String code) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        QueryList ql = new QueryList("GET booking:"+code);
        QueryResolution res = this.db.query(ql).get(0);
        if(res.isError())
            return null;
        else
            return mapper.readValue(res.message(), Booking.class);
    }

    private static List<Integer> listDiff(List<Integer> a, List<Integer> b){
        List<Integer> x = new ArrayList<>(a);
        x.removeAll(b);
        return x;
    }


    private static List<Integer> combineLists(List<Integer> a, List<Integer> b){
        List<Integer> combined = new ArrayList<>(a);
        combined.addAll(b);
        return combined;
    }



}