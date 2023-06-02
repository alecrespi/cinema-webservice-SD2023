import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Database{
    
    private ServerSocket dbSocket;
    private Socket clientSocket;

    //port setted to 4000
    public Database() throws IOException {
        this.dbSocket = new ServerSocket(4000);
    }

    public void listen() throws IOException {
        System.out.println("Database listening");
        while (true) {
            clientSocket = dbSocket.accept();
            (new RequestHandler(clientSocket)).start();
        }
    }



}
