package api.servlets;

import api.components.GameRoom;
import api.managers.FileManager;
import logic.exceptions.XmlContentException;
import ui.UserMoveInput;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by barakm on 10/10/2017
 */
@WebServlet(name = "PlayMoveServlet")
public class PlayMoveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String row = request.getParameter("row");
        String col = request.getParameter("col");
        String roomName = request.getParameter("roomName");
        String type = request.getParameter("type");

        UserMoveInput userMoveInput = new UserMoveInput(Integer.parseInt(row), Integer.parseInt(col));
        GameRoom gameRoom = FileManager.getRoomByName(roomName);

        if (!gameRoom.getCurrentPlayerName().equalsIgnoreCase(username)) {
            return;
        }

        String msg;
        try {
            String currentPlayerName = gameRoom.getCurrentPlayerName();
            String editedMsg;
            if (type.equals("personal")) {
                msg = gameRoom.getGameManager().playMove(userMoveInput, false);
            } else {
                msg = gameRoom.getGameManager().playMove(userMoveInput, true);
            }

            if (gameRoom.getGameManager().isPlayerWon()) {
                gameRoom.setWinnerName(currentPlayerName);
                gameRoom.getGameManager().playerWonMatchMessage();
                gameRoom.reset();
                editedMsg = currentPlayerName + " WON The Game!";
                response.setStatus(201);
            } else {
                editedMsg = currentPlayerName + ": " + msg;
                response.setStatus(200);
            }
            gameRoom.setLastPlayMsg(editedMsg);
        } catch (XmlContentException e) {
            response.getWriter().println(e.getMessage());
            response.setStatus(202);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
