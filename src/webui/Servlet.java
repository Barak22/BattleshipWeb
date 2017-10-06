package webui;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Servlet extends HttpServlet {

    private static final String GUEST_NAME_PARAMETER = "username";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<html a=\"test\">");
        out.println("<head>");
        out.println("<title>Servlet InitServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Visitor Name Input Servlet</h1>");
    }

    //TODO: Read about servlet context.
    private List<String> getGuestList() {
        List<String> guestsList = (List<String>) getServletContext().getAttribute("guestsList");
        if (guestsList == null) {
            guestsList = new ArrayList<>();
            getServletContext().setAttribute("guestsList", guestsList);
        }

        return guestsList;
    }
}
