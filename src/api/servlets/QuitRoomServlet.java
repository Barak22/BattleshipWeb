package api.servlets;

import api.components.GameRoom;
import api.managers.FileManager;
import logic.exceptions.XmlContentException;

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
        String userName = request.getParameter("userName");
        GameRoom gameRoom = FileManager.getRoomByName(roomName);
        PrintWriter writer = response.getWriter();

        if (gameRoom.getGameManager().isGameOn()) {
            gameRoom.getGameManager().quitMatch();
            String msg = getRelevantMessage(gameRoom.isMyTurn(userName));
            writer.print(msg);
            try {
                gameRoom.reset();
            } catch (XmlContentException e) {
                e.printStackTrace();
            }
            response.setStatus(201);
        } else {
            gameRoom.removeFirstPlayerName();
            gameRoom.decrementAndGet();
            response.setStatus(200);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String getRelevantMessage(boolean myTurn) {
        if (myTurn) {
            return "You quit";
        } else {
            return "You won!";
        }
    }
}
