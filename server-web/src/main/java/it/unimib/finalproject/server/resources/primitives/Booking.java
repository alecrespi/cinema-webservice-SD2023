package it.unimib.finalproject.server.resources.primitives;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimib.finalproject.server.utils.JSONable;
import it.unimib.finalproject.server.utils.MiscellaneousUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Booking implements JSONable {
    private String code;
    private int moviesession;
    private List<Integer> seats;

    public Booking() {
        this.seats = new ArrayList<>();
    }

    public Booking(String code, int moviesession) {
        this.code = code;
        this.moviesession = moviesession;
        this.seats = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMoviesession() {
        return moviesession;
    }

    public void setMoviesession(int moviesession) {
        this.moviesession = moviesession;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public boolean removeSeats(List<Integer> rem){
        return this.seats.removeAll(rem);
    }

    public boolean removeSeats(Integer[] rem){
        return this.removeSeats(Arrays.asList(rem));
    }


    // l is the moviesession total seats
    public boolean checkAvailability(List<Integer> c, List<Integer> toExclude) {
        List<Integer> l = new ArrayList<>(c);
        l.removeAll(toExclude);
        for (int element : this.getSeats())
            if (l.contains(element))
                return false;
        return true;
    }

    public boolean checkAvailability(List<Integer> l){
        return this.checkAvailability(l, new ArrayList<>());
    }

    public String assignCode(){
        this.setCode(MiscellaneousUtilities.generate(6).toUpperCase());
        return this.getCode();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "code='" + code + '\'' +
                ", moviesession=" + moviesession +
                ", seats=" + seats +
                '}';
    }

    @Override
    public String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }



}
