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

public class Main {
    private static final TopoGigiDB GIGI = TopoGigiDB.getInstance();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        System.out.println("STARTING APP");
        // setup starting data
        System.out.println("Start setup routine...");
        ArrayNode moviesessions = fetchJsonFromFile("./moviesessions.json");
        setupMovieSessions(moviesessions);
        System.out.println("...all setup uploaded");


        // setup socket connection
        System.out.println("Starting server...");
        ServerSocket ss = new ServerSocket(4000);
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

    public static void setupMovieSessions(ArrayNode moviesessions) throws JsonProcessingException {
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
        }
        GIGI.set("moviesession:*",mapper.writeValueAsString(moviesessions));
    }

}