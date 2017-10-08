package api.servlets;

import core.managers.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username").trim();

        if (!userName.isEmpty()) {
            if (SessionManager.addUser(userName) /*user name NOT already exists */) {
                Cookie cookie = new Cookie("username", userName);
                cookie.setPath("/");
                response.addCookie(cookie);
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
