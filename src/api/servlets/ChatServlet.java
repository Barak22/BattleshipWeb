package api.servlets;

import api.components.GameRoom;
import api.managers.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by barakm on 13/10/2017
 */
@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("room");
        String messageFromUser = request.getParameter("message");
        String userName = request.getParameter("userName");
        GameRoom theRoom = FileManager.getRoomByName(roomName);

        if (!messageFromUser.isEmpty()) {
            theRoom.addMessage(messageFromUser, userName);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.write("<div id=\"chatBody\" class=\"row\">");
        out.write("<div class=\"col-lg-2\"></div>");
        out.write("<div class=\"message-wrap col-lg-8\">");
        out.write("<div id=\"chatMessages\" class=\"msg-wrap\">");
        out.write("</div>");

        out.write("<div class=\"send-wrap \">");
        out.write("<textarea class=\"form-control send-message\" rows=\"3\" placeholder=\"Write a reply...\"></textarea>");
        out.write("</div>");

        out.write("<div class=\"col-lg-4\"></div>");
        out.write("<div class=\"col-lg-4 centerize-block\">");
        out.write("<button class=\"btn btn-success\" onclick=\"addMessage()\"> Send Message</button>");
        out.write("</div>");
        out.write("<div class=\"col-lg-4\"></div>");

        out.write("</div>");
        out.write("<div class=\"col-lg-2\"></div>");
        out.write("</div>");
    }
}
