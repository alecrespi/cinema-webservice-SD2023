package it.unimib.finalproject.server.resources.primitives;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieSession {
    private int id;
    private int movie;
    private int startTime;
    private int endTime;
    private int room;
    private List<Integer> seats;

    public MovieSession(){
        this.seats = new ArrayList<>();
    }

    public MovieSession(int id, int movie, int startTime, int endTime, int room) {
        this.id = id;
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public MovieSession(int id, int movie, int startTime, int endTime, int room, List<Integer> seats) {
        this.id = id;
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.seats = seats;
    }

    public MovieSession(int id, int movie, int startTime, int endTime, int room, Integer[] seats) {
        this.id = id;
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.seats = Arrays.asList(seats);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie() {
        return movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public void appendSeats(List<Integer> seats){
        this.seats.addAll(seats);
    }

    public void appendSeats(Integer[] seats){
        this.appendSeats(Arrays.asList(seats));
    }

    @Override
    public String toString() {
        return "MovieSession{" +
                "id='" + id + '\'' +
                ", movie=" + movie +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", room=" + room +
                ", seats=" + seats +
                '}';
    }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
