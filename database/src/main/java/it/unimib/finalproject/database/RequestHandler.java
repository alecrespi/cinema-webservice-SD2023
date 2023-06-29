package it.unimib.finalproject.database;

import it.unimib.finalproject.database.exceptions.*;
import it.unimib.finalproject.database.topogigiengine.TopoGiGiInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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

            String response = handleEvaluation(script);

            sendResponseBatch(response);

            this.client.close();
            System.out.println(Thread.currentThread().getName() + " : Client served");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Thread.currentThread().getName() + " : socket lost");
        }

    }

    private String getScript() throws IOException {
        List<String> builder = new ArrayList<>();
        do
            builder.add(this.input.readLine());
        while(this.input.ready());

        return String.join("\n",builder);
    }

    private void sendResponseBatch(String batch){
        this.output.println(batch);
        this.output.flush();
    }

    private String handleEvaluation(String script){
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
            }catch(KeyAlreadyBoundException | UnreleasableKeysException e){
                response.add("503");
                break;
            }catch(UndefinedKeyException e){
                response.add("404");
                break;
            }catch(Exception e){
                response.add("500");
            }
        }

        return String.join("\n", response);
    }
}
