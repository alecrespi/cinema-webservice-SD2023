package movieSession;

import exceptions.DBNotAliveException;
import flavio.FlavioEngine;
import utils.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movieSession/*")
public class MovieSessionServlet extends HttpServlet {

    private final FlavioEngine db;

    public MovieSessionServlet() {
        super();
        this.db = new FlavioEngine();
    }

    // GET/movieSession/{idMovieSession}
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException{

        String requestPath = req.getPathInfo();

        if (requestPath == null || requestPath.equals("/")) {
            // here handle GET /movieSession
            String movieSession = Utils.splitRoute(req)[1];

            String query = "GETTING: " + movieSession;
            try {
                this.db.ask(movieSession);
                String data = this.db.receive();
                res.getWriter().print(data);
            } catch (DBNotAliveException e) {
                res.setStatus(503); //server not ready to handle the request
                res.getWriter().print("Service Unavailable");
            }

        } else {
            // here handle GET /movieSession/{idMovieSession}
            String idMovieSession = Utils.splitRoute(req)[1];

            String query = "GETTING: " + idMovieSession;
            try {
                this.db.ask(query);
                String data = this.db.receive();
                res.getWriter().print(data);
            } catch (DBNotAliveException e) {
                res.setStatus(503); //server not ready to handle the request
                res.getWriter().print("Service Unavailable");
            }
        }
    }

}
