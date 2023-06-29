package it.unimib.finalproject.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.unimib.finalproject.database.exceptions.TimeOverlapException;
import it.unimib.finalproject.database.exceptions.TimeRangeException;
import it.unimib.finalproject.database.topogigiengine.TopoGigiDB;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    private static final TopoGigiDB GIGI = TopoGigiDB.getInstance();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        System.out.println("STARTING APP");
        // setup starting data
        System.out.println("Start setup routine...");
        setupMovieSessions(fetchJsonFromFile("./persistence/moviesessions.json"), 200);
        setupMovies(fetchJsonFromFile("./persistence/movies.json"));
        setupBookings(fetchJsonFromFile("./persistence/bookings.json"));
        System.out.println("...all setup uploaded");


        // setup socket connection
        System.out.println("Starting server...");
        ServerSocket ss = new ServerSocket(3030);
        System.out.println("Listening...");
        while(true){
            Socket s = ss.accept();
            System.out.println("Inbound connection: " + s.toString());
            (new RequestHandler(s)).start();
        }
    }

    public static ArrayNode fetchJsonFromFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        return (ArrayNode) mapper.readTree(file);
    }

    public static void setupMovieSessions(ArrayNode moviesessions, int seatsPerSession) throws JsonProcessingException {
        for (JsonNode ms : moviesessions) {
            long currentStartTime = ms.get("startTime").asLong();
            long currentEndTime = ms.get("endTime").asLong();
            long currentRoom = ms.get("room").asInt();

            if(currentEndTime <= currentStartTime)
                throw new TimeRangeException();

            for(JsonNode sus : moviesessions) {    // check if time are overlapping
                long susStartTime = sus.get("startTime").asLong();
                long susEndTime = sus.get("endTime").asLong();
                long susRoom = sus.get("room").asInt();
                if (ms != sus &&
                        currentRoom == susRoom &&
                        susStartTime <= currentStartTime &&
                        currentStartTime <= susEndTime
                )   throw new TimeOverlapException();
            }
            GIGI.set("moviesession:" + ms.get("id").asText(), mapper.writeValueAsString(ms));
            GIGI.set("moviesession:" + ms.get("id").asText() + ":seats", "[]");
        }
        GIGI.set("moviesession:*", mapper.writeValueAsString(moviesessions));
    }

    public static void setupBookings(ArrayNode bookings) throws JsonProcessingException {
        for (JsonNode booking : bookings) {
            GIGI.set("booking:" + booking.get("code").asText(), mapper.writeValueAsString(booking));
            // saving moviesessionID and Seats reserved
            int msId = booking.get("moviesession").asInt();
            int[] seats = mapper.treeToValue(booking.get("seats"), int[].class);
            // retrieve the total reserved seats
            int[] totalSeats = mapper.readValue( GIGI.get("moviesession:" + msId + ":seats"), int[].class);
            totalSeats = combineArray(totalSeats, seats);
            GIGI.set("moviesession:" + msId + ":seats", mapper.writeValueAsString(totalSeats));
        }
    }

    public static void setupMovies(ArrayNode movies) throws JsonProcessingException {
        for (JsonNode movie : movies)
            GIGI.set("movie:" + movie.get("id").asText(), mapper.writeValueAsString(movie));
        GIGI.set("movie:*", mapper.writeValueAsString(movies));
    }

    private static int[] combineArray(int[] array1, int[] array2){
        int[] combinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
        return combinedArray;
    }

}