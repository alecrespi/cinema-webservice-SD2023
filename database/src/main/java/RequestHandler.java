import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread{
    private final Socket client;
    public RequestHandler(Socket socket){
        this.client = socket;
    }

    @Override
    public void run() {
        try {
            String request = getRequest();
            System.out.println(Thread.currentThread().getName() + " serving : " + request);

            String response = evalQuery(request);

            sendResponse(response);
            this.client.close();
            System.out.println(Thread.currentThread().getName() + " : Client served");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Thread.currentThread().getName() + " : socket lost");
        }

    }

    public String getRequest() throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        String req = buffer.readLine();
        return req;
    }

    public void sendResponse(String response) throws IOException {
        PrintWriter out = new PrintWriter(this.client.getOutputStream());
        out.write(response);
        out.flush();
    }

    public String evalQuery(String query){
        String[] squery = query.split(" ", 3);
        String rtr = "";
        for(String q : squery)
            rtr += q + " ; ";

        return rtr;
    }

}
