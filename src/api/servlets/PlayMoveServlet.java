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
        String row = request.getParameter("row");
        String col = request.getParameter("col");
        String roomName = request.getParameter("roomName");
        String type = request.getParameter("type");

        UserMoveInput userMoveInput = new UserMoveInput(Integer.parseInt(row), Integer.parseInt(col));
        GameRoom gameRoom = FileManager.getRoomByName(roomName);
        String msg;
        try {
            if (type.equals("personal")) {
                msg = gameRoom.getGameManager().playMove(userMoveInput, false);
            } else {
                msg = gameRoom.getGameManager().playMove(userMoveInput, true);
            }
            String editedMsg = gameRoom.getCurrentPlayerName().split(":")[0] + ": " + msg;
            gameRoom.setLastPlayMsg(editedMsg);
            response.getWriter().println(editedMsg);
            response.setStatus(200);
        } catch (XmlContentException e) {
            response.getWriter().println(e.getMessage());
            response.setStatus(201);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
