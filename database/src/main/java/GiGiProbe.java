
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GiGiProbe {
    public static final int PORT = 4000;
    public static final String ADDRESS = "localhost";


    public static void main(String[] args) {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {

                System.out.print("TopoGiGi> ");
                // reading query from keyboard
                String message = kb.readLine();

                Socket socket = new Socket(ADDRESS, PORT);
                //System.out.println("Connected to " + ADDRESS + ":" + PORT);

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                if (message.equalsIgnoreCase("quit")) break;

                // sending query to server
                output.println(message);

                System.out.println(input.readLine());
//                System.out.println("Server response: " + input.readLine());
//                System.out.println("Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
