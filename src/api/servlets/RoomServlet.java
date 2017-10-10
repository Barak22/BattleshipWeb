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
import java.util.stream.Collectors;

/**
 * Created by barakm on 10/10/2017
 */
@WebServlet(name = "RoomServlet")
public class RoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String roomName = request.getParameter("roomName");
        GameRoom roomGame = FileManager.getGameFiles().stream()
                                       .filter(gameFile -> gameFile.getRoomName().equals(roomName))
                                       .collect(Collectors.toList()).get(0);

        int numOfPlayers = roomGame.getNumOfPlayers();

        if (numOfPlayers == 2) {
            writer.println("The game already started");
            response.setStatus(201);
        } else if (roomGame.increamentAndGet() == 2) {
            writer.println("Game stated");
            response.setStatus(200);
        } else {
            response.setStatus(202);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
