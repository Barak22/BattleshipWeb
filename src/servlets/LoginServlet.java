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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        PrintWriter out = response.getWriter();
        if (userName != null && !userName.isEmpty()) {
            if (SessionManager.addUser(userName) /*user name NOT already exists */) {
                response.sendRedirect("/pages/welcome.html?username=" + userName);
            } else {
                response.sendRedirect("/pages/login.html?msg=exists");
            }
        } else {
            response.sendRedirect("/pages/login.html?msg=empty");
        }
    }
}
