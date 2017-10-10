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

@WebServlet(name = "GetBoardsServlet")
public class GetBoardsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        GameRoom theRoom = FileManager.getRoomByName(roomName);
        PrintWriter out = response.getWriter();
        if (theRoom != null) {
            buildBoardsHTML(out, theRoom);
        } else {
            out.write("Sorry, the room is no longer exists"); // NOT SHOULD HAPPEN IN A VALID GAME LINE
            response.setStatus(500);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void buildBoardsHTML(PrintWriter out, GameRoom theRoom) {

    }
}
