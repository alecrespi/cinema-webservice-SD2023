import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler extends Thread{
    private final Socket clientSocket;
    public RequestHandler(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        String messageFromClient = "";

        try {
            messageFromClient = handleReceiveMessage();
            System.out.println("Message from client: " + messageFromClient);

            handleResponseMessage(messageFromClient);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //ho paura che se tolgo l'interrupt poi non funziona più niente
        interrupt();
    }


    //return the actual client message
    public String handleReceiveMessage() throws IOException {
        //get the client socket input (in byte format)
        InputStream socketInputStream = this.clientSocket.getInputStream();
        BufferedInputStream buffer = new BufferedInputStream(socketInputStream);

        byte[] byteArray = new byte[255];
        int messageLength = buffer.read(byteArray);

        //convert the client socket input from byte to String
        return new String(byteArray, 0, messageLength);
    }

    //handleResponseMessage send a response message back to the client
    public void handleResponseMessage(String response) throws IOException {
        PrintWriter outputStream = new PrintWriter(this.clientSocket.getOutputStream());

        outputStream.write(response);
        outputStream.flush();
        outputStream.close();
    }


}
