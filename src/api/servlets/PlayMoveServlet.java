package api.servlets;

import api.components.GameRoom;
import api.enums.CookieTypes;
import api.managers.FileManager;
import api.managers.SessionManager;
import api.utils.CookieUtils;
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
        String cookieUserName = CookieUtils.getCookieValue(request.getCookies(), CookieTypes.USER_NAME);
        if (!SessionManager.isUserExists(cookieUserName)) {
            response.getWriter().print("You logged out");
            response.setStatus(501);
            return;
        }

        String row = request.getParameter("row");
        String col = request.getParameter("col");
        String roomName = request.getParameter("roomName");
        String type = request.getParameter("type");

        UserMoveInput userMoveInput = new UserMoveInput(Integer.parseInt(row), Integer.parseInt(col));
        GameRoom gameRoom = FileManager.getRoomByName(roomName);

        if (!gameRoom.getCurrentPlayerName().equalsIgnoreCase(cookieUserName)) {
            return;
        }

        String msg;
        try {
            String currentPlayerName = gameRoom.getCurrentPlayerName();
            if (type.equals("personal")) {
                msg = gameRoom.getGameManager().playMove(userMoveInput, false);
            } else {
                msg = gameRoom.getGameManager().playMove(userMoveInput, true);
            }
            String editedMsg = currentPlayerName + ": " + msg;
            gameRoom.setLastPlayMsg(editedMsg);
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
