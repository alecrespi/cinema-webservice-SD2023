package cinemamanager;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class SeatSerializer extends JsonSerializer<Seat> {

    @Override
    public void serialize(Seat seat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(seat.toEasyString());
    }

}