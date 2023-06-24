import cinemamanager.Movie;
import cinemamanager.MovieSession;
import exceptions.InvalidTimeRangeException;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws InvalidTimeRangeException {
        Movie avengers = new Movie("Avengers: End Game", "Topolino", 2020, 4.8);
        MovieSession mv = new MovieSession("kjcnasdc", avengers, System.currentTimeMillis()/1000, System.currentTimeMillis()/1000 + 2 * 60 * 60);
        System.out.println(mv.toJSON());


//        try {
//            Database db = new Database();
//            db.listen();
//        } catch (IOException e) {
//            System.out.print("Something went wrong: ");
//            System.out.println(e.getMessage());
//        }
    }

}