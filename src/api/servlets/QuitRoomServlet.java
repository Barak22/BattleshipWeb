package api.servlets;

import api.components.GameRoom;
import api.managers.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by barakm on 11/10/2017
 */
@WebServlet(name = "QuitRoomServlet")
public class QuitRoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        GameRoom gameRoom = FileManager.getRoomByName(roomName);

        if (gameRoom.getGameManager().isGameOn()) {
            String currentPlayerName = gameRoom.getCurrentPlayerName();
            gameRoom.getGameManager().quitMatch();
            gameRoom.setWinnerName(gameRoom.getCurrentPlayerName().equals(gameRoom.getFirstPlayerName()) ? gameRoom.getSecondPlayerName() : gameRoom.getFirstPlayerName());
            String msg = currentPlayerName + " has quit from the match";
            gameRoom.setLastPlayMsg(msg);
            gameRoom.reset();
            response.setStatus(201);
        } else {
            // Game is already over - do nothing
            response.setStatus(202);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        String username = request.getParameter("userName");
        GameRoom gameRoom = FileManager.getRoomByName(roomName);

        gameRoom.removeUserName(username);
    }
}
