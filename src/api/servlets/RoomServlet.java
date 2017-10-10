package api.servlets;

import api.components.GameFile;
import api.managers.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by barakm on 10/10/2017
 */
@WebServlet(name = "RoomServlet")
public class RoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        GameFile roomGame = FileManager.getGameFiles().stream()
                                       .filter(gameFile -> gameFile.getRoomName().equals(roomName))
                                       .collect(Collectors.toList()).get(0);

        if (roomGame.increamentAndGet() == 2) {
            //start game.
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
