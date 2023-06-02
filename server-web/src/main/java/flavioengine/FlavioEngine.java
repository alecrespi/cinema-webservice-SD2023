package flavioengine;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FlavioEngine {
    public static final String ADDRESS = "localhost";
    public static final int PORT = 4000;
    public static final int MAX_BUFFER_SIZE = 4096;

    private final String address;
    private final int port;
    private Socket socket;
    private boolean isStreamOpen;
    private OutputStream out;
    private InputStream in;

    public FlavioEngine(String address, int port){
        this.socket = null;
        this.address = address;
        this.port = port;
        this.isStreamOpen = false;
    }

    public FlavioEngine(){
        this.socket = null;
        this.address = ADDRESS;
        this.port = PORT;
        this.isStreamOpen = false;
    }


    public boolean isStreamOpen() {
        return isStreamOpen;
    }

    public boolean ask(String query){
        try {
            // setup connection TCP to DB Server
            this.socket = new Socket(this.address, this.port);
            this.out = this.socket.getOutputStream();
            this.in = this.socket.getInputStream();

            this.out.write(query.getBytes());
            this.out.flush();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String receive(int length) throws IOException {
        byte[] buffer = new byte[length];
        int bytesRead = this.in.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    public String receive() throws IOException {
        byte[] buffer = new byte[MAX_BUFFER_SIZE];
        int bytesRead = this.in.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

}