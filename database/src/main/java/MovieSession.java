public class MovieSession {
    private final int id;
    private final String film;
    private final int room;
    private final long startTime;
    private final long endTime;

    public MovieSession(int id, String film, int room, long startTime, long endTime) {
        this.id = id;
        this.film = film;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public String getFilm() {
        return film;
    }

    public int getRoom() {
        return room;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
