package topogigiengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


// this class is for testing purposes only
public class GiGiProbe {
    public static final int PORT = 4000;
    public static final String ADDRESS = "localhost";
    private static BufferedReader input;
    private static PrintWriter output;

    public static void main(String[] args) {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = null;
        while (true) {
            try {
                // reading query from keyboard
                System.out.print("TopoGiGi> ");
                ArrayList<String> script = new ArrayList<>();
                String line = kb.readLine();
                if (line.equalsIgnoreCase("quit")) break;
                while(!line.isEmpty()){
                    script.add(line);
                    line = kb.readLine();
                }

                // connect to server
                socket = new Socket(ADDRESS, PORT);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream());


                // sending query to server
                sendScript(script);

                // listening for response
                List<String> res = getResponseBatch();
                System.out.println("~> " + String.join("\n~> ", res));
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private static List<String> getResponseBatch() throws IOException {
        List<String> rtr = new ArrayList<>();
        String message = input.readLine();
        while (message != null) {
            rtr.add(message);
            if (!input.ready())     break;
            message = input.readLine(); // Read the next line
        }

        return rtr;
    }

    private static void sendScript(List<String> batch){
        System.out.println("---------");
        System.out.println(String.join("\n\r", batch));
        System.out.println("---------");
        output.write(String.join("\n\r", batch));
        output.flush();
    }
}
