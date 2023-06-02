import flavioengine.FlavioEngine;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    private FlavioEngine db;

    public HelloServlet() {
        super();
        this.db = new FlavioEngine();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String x = req.getParameter("x");
        System.out.println("GET " + req.getRequestURI());
        if(this.db.ask("Bucchin: " + x)){
            String asd = this.db.receive();
            res.getWriter().print(asd);
        }
        else
            res.getWriter().print("Connection refused");

    }

}
