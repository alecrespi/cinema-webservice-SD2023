package cinemamanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import exceptions.InvalidTimeRangeException;
import exceptions.InvalidSeatLocatorException;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MovieSession {
    private final String id;
    private final Movie movie;
    private final long startTime;
    private final long endTime;
    private final SortedSet<Seat> bookedSeats;
    private final ReadWriteLock lock;

    public MovieSession(String id, Movie movie, long startTime, long endTime) throws InvalidTimeRangeException {
        if(startTime >= endTime)
            throw new InvalidTimeRangeException();
        this.id = id;
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedSeats = new TreeSet<>();
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Filters the seats Set by Booking code.
     * @param bookingCode : a valid booking code
     * @return A list of matched seats
     */
    public List<Seat> getBookedSeats(@NotNull String bookingCode){
        lock.readLock().lock();
        try{
            boolean codeFound = false;
            List<Seat> rtr = new ArrayList<>();
            for(Seat current : this.bookedSeats){
                if(current.getBookingCode().equals(bookingCode)) {
                    rtr.add(current);
                    codeFound = true;
                }else if(codeFound) break;
            }
            return rtr;
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Attempts to reserve a Collection of seats to a specific Booking code
     * @param seats : Collection of string that easy-represent a Seat
     * @param bookingCode : identifier of a booking
     * @return true if successfully booked the seats, false if a seats is already reserved
     * @throws InvalidSeatLocatorException
     */
    public boolean bookSeats(@NotNull Collection<String> seats, @NotNull String bookingCode) throws InvalidSeatLocatorException {
        lock.writeLock().lock();
        try{
            ArrayList<Seat> parsedSeats = new ArrayList<>(seats.size());
            for(String seat : seats) {
                Seat p = Seat.parseSeat(seat);
                p.setBookingCode(bookingCode);
                if (this.bookedSeats.contains(p))
                    return false;
                parsedSeats.add(p);
            }
            this.bookedSeats.addAll(parsedSeats);
            return true;
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes all seats reserved at bookingCode that matches with the given Collection of strings (locator of seat)
     * @param seats : a Collection of String that are locator of seat
     * @param bookingCode : the Booking code of the seats to remove
     * @return The return value given by Collection::removeIf
     */
    public boolean removeSeats(@NotNull Collection<String> seats, @NotNull String bookingCode) {
        lock.writeLock().lock();
        try{
            return this.bookedSeats.removeIf(seat -> {
                for(String locator : seats){
                    try {
                        if(seat.getBookingCode().equals(bookingCode) && seat.equalsToLocator(locator))
                            return true;
                    } catch (InvalidSeatLocatorException e) { /* suppressing exception */ }
                }
                return false;
            });
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes all seats reserved at bookingCode that matches with the given Collection of strings (locator of seat)
     * @param bookingCode : the Booking code of the seats to remove
     * @return The return value given by Collection::removeIf
     */
    public boolean removeBooking(@NotNull String bookingCode){
        lock.writeLock().lock();
        try{
            return this.bookedSeats.removeIf(seat -> seat.getBookingCode().equals(bookingCode));
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Converts the object in JSON representation
     * Attention: Seat objects are converted in easy readable strings (locator), for transferring reasons
     * @return String representing JSON object
     */
    public String toJSON(){
        lock.readLock().lock();
        try{
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
        }finally {
            lock.readLock().unlock();
        }
    }

    // INTERFACE METHODS
    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }
    public List<Seat> getBookedSeats(){
        lock.readLock().lock();
        try{
            return List.copyOf(bookedSeats);
        }finally {
            lock.readLock().unlock();
        }
    }

}
