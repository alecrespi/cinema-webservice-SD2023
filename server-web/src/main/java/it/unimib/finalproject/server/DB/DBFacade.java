package it.unimib.finalproject.server.DB;

import it.unimib.finalproject.server.utils.AppSettings;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class DBFacade {
    private static DBFacade instance = null;
    private final String address;
    private final int port;
    private PrintWriter output;
    private BufferedReader input;

    private DBFacade(){
        this.address = AppSettings.DB_ADDRESS;
        this.port = AppSettings.DB_PORT;
    }

    public static DBFacade getInstance(){
        if(instance == null)
            instance = new DBFacade();
        return instance;
    }



    /**
     *
     * @param script a sequence of instructions specified in TopoGiGi implementation
     * @return the response string that could represent success or failure
     */
    public synchronized ScriptResolution query(String script) throws IOException {
        List<String> queries = Arrays.asList(script.split("\n"));
        Socket s = new Socket(this.address, this.port);
        this.output = new PrintWriter(s.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(s.getInputStream()));

        sendScript(script);
        List<String> r = getResponse();
        return pairQueriesToResolution(queries, r);
    }


    public ScriptResolution query(QueryList script) throws IOException {
        return this.query(script.toString());
    }


    private List<String> getResponse() throws IOException {
        List<String> builder = new ArrayList<>();
        do
            builder.add(this.input.readLine());
        while(input.ready());

        return builder;
    }


    private void sendScript(String script){
        output.println(script);
        output.flush();
    }


    private ScriptResolution pairQueriesToResolution(List<String> queries, List<String> resolutions){
        ScriptResolution rtr = new ScriptResolution();
        // we assume that resolutions.size() <= queries.size()
        for(int i = 0; i < resolutions.size(); i++)
            rtr.add(new QueryResolution(queries.get(i), resolutions.get(i)));
        return rtr;
    }
}
