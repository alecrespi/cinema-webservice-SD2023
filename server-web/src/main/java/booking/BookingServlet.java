package booking;
import exceptions.DBNotAliveException;
import flavio.FlavioEngine;
import utils.Utils;

import java.io.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/booking/*")
public class BookingServlet extends HttpServlet {

    private final FlavioEngine db;

    public BookingServlet() {
        super();
        this.db = new FlavioEngine();
    }

    // GET /booking/{bookingCode}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String bookingCode = Utils.splitRoute(req)[1];   // get Booking code from URI

        String query = "GETTING: " + bookingCode;
        try{
            this.db.ask(query);
            String data = this.db.receive();
            res.getWriter().print(data);
        }catch(DBNotAliveException e){
            res.setStatus(503);
            res.getWriter().print("Service Unavailable");
        }
    }

    // POST /booking/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String body = Utils.getBody(req);

        String query = "POSTING: " + body;
        try{
            this.db.ask(query);
            String data = this.db.receive();
            res.getWriter().print(data);
        }catch(DBNotAliveException e){
            res.setStatus(503);
            res.getWriter().print("Service Unavailable");
        }
    }

    // PUT /booking/{bookingCode}
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String bookingCode = Utils.splitRoute(req)[1];
        String body = Utils.getBody(req);

        String query = "PUTTING: " + bookingCode + " - " + body;
        try{
            this.db.ask(query);
            String data = this.db.receive();
            res.getWriter().print(data);
        }catch(DBNotAliveException e){
            res.setStatus(503);
            res.getWriter().print("Service Unavailable");
        }
    }

    // PUT /booking/{bookingCode}
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String bookingCode = Utils.splitRoute(req)[1];

        String query = "DELETE: " + bookingCode;
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
