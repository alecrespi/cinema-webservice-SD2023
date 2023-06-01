package flaviodriver;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConfigFlavioSocket{
    public static final String ADDRESS = "172.20.10.3";
    public static final int PORT = 4000;
    public static final int MAX_BUFFER_SIZE = 4096;

    public static String sendFlavio(String query){
        Socket f;
        try {
            f = new Socket(ADDRESS, PORT);
            OutputStream outputStream = f.getOutputStream();
            InputStream inputStream = f.getInputStream();

            byte[] squery = query.getBytes();
            outputStream.write(squery);
            outputStream.flush();

            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            int bytesRead = inputStream.read(buffer);
            return new String(buffer, 0, bytesRead);

        } catch (IOException e) {
            System.out.println();
            return "Connection refused";
        }
    }

}