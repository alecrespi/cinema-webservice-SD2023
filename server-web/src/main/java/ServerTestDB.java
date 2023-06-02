import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTestDB {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(4000);
        System.out.println("START SERVER");
        Socket s = ss.accept();
        System.out.println("connected");

        byte[] buffer = new byte[1024];
        int bytesRead = s.getInputStream().read(buffer);
        String msg = new String(buffer, 0, bytesRead);
        System.out.println("received: " + msg);

        System.out.println("sending");
        OutputStream o = s.getOutputStream();
        o.write("HELLO FROM DB SERVER".getBytes());
    }
}
