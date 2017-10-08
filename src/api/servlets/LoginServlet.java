package api.servlets;

import api.components.User;
import api.enums.CookieTypes;
import api.enums.WebStatus;
import api.managers.SessionManager;

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
            if (SessionManager.addUser(new User(userName, WebStatus.LOBBY)) == null /*user name NOT already exists */) {
                Cookie cookie = new Cookie(CookieTypes.USER_NAME.getValue(), userName);
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
        Cookie[] cookies = request.getCookies();
        boolean hasCookie = false;

        if (cookies == null) {
            cookies = new Cookie[0];
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CookieTypes.USER_NAME.getValue())) {
                response.setStatus(200);
                hasCookie = true;
            }
        }

        if (!hasCookie) {
            response.setStatus(500);
        }
    }
}
