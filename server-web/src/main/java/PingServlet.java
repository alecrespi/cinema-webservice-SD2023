import exceptions.DBNotAliveException;
import flavio.FlavioEngine;

import java.io.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/ping")
public class PingServlet extends HttpServlet {

    private final FlavioEngine db;

    public PingServlet() {
        super();
        this.db = new FlavioEngine();
    }

    // GET /ping
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String query = "hello from WEBSERVER to DB SERVER";
        try{
            this.db.ask(query);
            String data = this.db.receive();
            res.getWriter().print(data);
        }catch(DBNotAliveException e){
            res.setStatus(503);
            res.getWriter().print("Service Unavailable");
        }
    }

}
