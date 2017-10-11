package api.servlets;

import api.components.GameRoom;
import api.enums.CookieTypes;
import api.managers.FileManager;
import api.managers.SessionManager;
import api.utils.CookieUtils;

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
        String cookieUserName = CookieUtils.getCookieValue(request.getCookies(), CookieTypes.USER_NAME);
        if (!SessionManager.isUserExists(cookieUserName)) {
            response.getWriter().print("You logged out");
            response.setStatus(501);
            return;
        }

        PrintWriter writer = response.getWriter();
        String roomName = request.getParameter("roomName");
        String playerName = request.getParameter("playerName");
        GameRoom roomGame = FileManager.getGameFiles().stream()
                                       .filter(gameFile -> gameFile.getRoomName().equals(roomName))
                                       .collect(Collectors.toList()).get(0);

        int numOfPlayers = roomGame.getNumOfPlayers();

        if (roomGame.isPlayerAlreadyIn(playerName)) {
            writer.println("You are already in the room");
            response.setStatus(203);
        } else if (numOfPlayers == 2) {
            writer.println("The game already started");
            response.setStatus(201);
        } else if (roomGame.incrementAndGet() == 2) {
            writer.println("Game stated");
            roomGame.setPlayerName(playerName);
            response.setStatus(200);
        } else {
            roomGame.setPlayerName(playerName);
            response.setStatus(200);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    }
}
