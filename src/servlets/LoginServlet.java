package servlets;

import webmanagers.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        PrintWriter out = response.getWriter();
        userName = userName.trim();
        if (userName != null && !userName.isEmpty()) {
            if (SessionManager.addUser(userName) /*user name NOT already exists */) {
                response.setStatus(200);
            } else {
                response.setStatus(400);
            }
        } else {
            response.setStatus(500);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
