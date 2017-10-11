package api.servlets;

import api.components.User;
import api.managers.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OnlineUsersServlet")
public class OnlineUsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if (!SessionManager.getUsers().isEmpty()) {
            for (User user : SessionManager.getUsers()) {
                out.write("<button type=\"button\" class=\"list-group-item list-group-item-action\">");
                out.write(user.getName());
                out.write("</button>");
            }
        } else {
            out.write("There are no online users to show");
        }
    }
}
