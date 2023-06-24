package cinemamanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import exceptions.InvalidTimeRangeException;
import exceptions.InvalidSeatLocatorException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class MovieSession {
    private final String code;
    private final Movie movie;
    private final long startTime;
    private final long endTime;
    private ConcurrentSkipListSet<Seat> bookedSeats;


    public MovieSession(String code, Movie movie, long startTime, long endTime) throws InvalidTimeRangeException {
        if(startTime >= endTime)
            throw new InvalidTimeRangeException();
        this.code = code;
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedSeats = new ConcurrentSkipListSet<>();
    }

    public List<Seat> getBookedSeats(String bookingCode){
        boolean codeFound = false;
        List<Seat> rtr = new ArrayList<>();
        for(Seat current : this.bookedSeats){
            if(current.getBookingCode().equals(bookingCode)) {
                rtr.add(current);
                codeFound = true;
            }else if(codeFound) break;
        }
        return rtr;
    }

    /**
     *
     * @param seats
     * @param bookingCode
     * @return true if successfully booked the seats, false if a seats is already reserved
     * @throws InvalidSeatLocatorException
     */
    public boolean bookSeats(@NotNull Collection<String> seats, String bookingCode) throws InvalidSeatLocatorException {
        ArrayList<Seat> parsedSeats = new ArrayList<>(seats.size());
        for(String seat : seats) {
            Seat p = Seat.parseSeat(seat);
            if (this.bookedSeats.contains(p))
                return false;
            parsedSeats.add(p);
        }
        this.bookedSeats.addAll(parsedSeats);
        return true;
    }

    // INTERFACE METHODS
    public String getCode() { return code; }
    public Movie getMovie() { return movie; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    public ConcurrentSkipListSet<Seat> getBookedSeats(){ return bookedSeats; }

    /**
     * Converts the object in JSON representation
     * Attention: Seat objects are converted in easy readable strings, for transferring reasons
     * @return
     */
    public String toJSON(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Seat.class, new SeatSerializer());
        objectMapper.registerModule(module);

        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
