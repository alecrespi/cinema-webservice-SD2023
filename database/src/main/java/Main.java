import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Database db = new Database();
            db.listen();
        } catch (IOException e) {
            System.out.print("Something went wrong: ");
            System.out.println(e.getMessage());
        }
    }

}