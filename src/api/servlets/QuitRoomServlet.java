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
 * Created by barakm on 11/10/2017
 */
@WebServlet(name = "QuitRoomServlet")
public class QuitRoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        GameRoom gameRoom = FileManager.getRoomByName(roomName);
        PrintWriter writer = response.getWriter();

        if (gameRoom.getGameManager().isGameOn()) {
            writer.print(gameRoom.getGameManager().quitMatch());
        } else {
            gameRoom.removeFirstPlayerName();
            gameRoom.decrementAndGet();
        }

        response.setStatus(200);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
