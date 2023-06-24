import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import exceptions.TimeOverlapException;
import exceptions.TimeRangeException;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        TopoGigiDB gigi = new TopoGigiDB();
        ArrayNode moviesessions = fetchJsonFromFile("./moviesessions.json");
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
            gigi.set("moviesession:" + ms.get("id").asText(), mapper.writeValueAsString(ms));
        }
        gigi.set("moviesession:*",mapper.writeValueAsString(moviesessions));

        for(int i = 0; i < moviesessions.size(); i++)
            System.out.println(gigi.get("moviesession:ms" + i));
        System.out.println(gigi.get("moviesession:*"));

    }


    public static ArrayNode fetchJsonFromFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        return (ArrayNode) mapper.readTree(file);
    }
}