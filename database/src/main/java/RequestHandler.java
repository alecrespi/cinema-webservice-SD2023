import exceptions.*;
import topogigiengine.TopoGiGiInterpreter;
import topogigiengine.TopoGigiDB;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestHandler extends Thread{
    private final Socket client;
    private final TopoGiGiInterpreter gigiSQL;
    private final BufferedReader input;
    private final PrintWriter output;

    public RequestHandler(Socket socket) throws IOException {
        this.client = socket;
        this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.output = new PrintWriter(this.client.getOutputStream());
        this.gigiSQL = TopoGiGiInterpreter.getInstance();
    }

    @Override
    public void run() {
        try {
            String script = getScript();
            System.out.println(Thread.currentThread().getName() + " : serving...");
            String[] reqs = script.split("\n");
            ArrayList<String> response = new ArrayList<>();
            for(String request : reqs){

                try{
                    response.add("#" + this.gigiSQL.eval(request));
                }catch(UnknownCommandException | InvalidParametersException e){
                    response.add("400");
                    break;
                }catch(UnprocessableEntityException e){
                    response.add("422");
                    break;
                }catch(KeyAlreadyBoundException e){
                    response.add("503");
                    break;
                }catch(UndefinedKeyException e){
                    response.add("404");
                    break;
                }
            }

            sendResponseBatch(response);

            this.client.close();
            System.out.println(Thread.currentThread().getName() + " : Client served");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Thread.currentThread().getName() + " : socket lost");
        }

    }

    private String getScript() throws IOException {
        StringBuilder builder = new StringBuilder();
        String message = this.input.readLine();
        while (message != null) {
            System.out.println(message);
            builder.append(message);
            if (!this.input.ready())     break;
            System.out.println("here");
            message = this.input.readLine(); // Read the next line
        }

        return builder.toString();
    }

    private void sendResponseBatch(List<String> batch){
        this.output.write(String.join("\n",batch));
        this.output.flush();
    }
}
