package api.servlets;

import api.components.User;
import api.enums.WebStatus;
import api.managers.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username").trim();

        if (!username.isEmpty()) {
            if (SessionManager.addUser(new User(username, WebStatus.LOBBY)) == null /*user name NOT already exists */) {
                HttpSession session = request.getSession();
                session.setAttribute("LoggedInUsername", username);
                response.setStatus(200);
            } else {
                response.setStatus(400);
            }
        } else {
            response.setStatus(202);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object username = session.getAttribute("LoggedInUsername");
        if (username != null) {
            response.getWriter().write(username.toString());
            response.setStatus(200);
        } else {
            response.setStatus(201);
        }
    }
}
