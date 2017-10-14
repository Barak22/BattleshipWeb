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
        String playerName = request.getParameter("playerName");
        GameRoom roomGame = FileManager.getGameFiles().stream()
                .filter(gameFile -> gameFile.getRoomName().equals(roomName))
                .collect(Collectors.toList()).get(0);

        int numOfPlayers = roomGame.getNumOfPlayers();

        if (roomGame.isWatcher(playerName)) {
            writer.write("You can't be a watcher and a player at the same time - play fair!");
            response.setStatus(203);
            return;
        }

        if (roomGame.isPlayerAlreadyIn(playerName) && roomGame.isPlayersDataAccurate()) {
            writer.println("You are already in the room");
            response.setStatus(203);
        } else if (numOfPlayers == 2) {
            writer.println("The game already started");
            response.setStatus(201);
        } else if (roomGame.incrementAndGet() == 2) {
            writer.println("Game started");
            roomGame.setPlayerName(playerName);
            response.setStatus(200);
        } else {
            roomGame.hardReset();
            roomGame.setPlayerName(playerName);
            response.setStatus(200);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        String playerName = request.getParameter("playerName");
        String action = request.getParameter("action");
        GameRoom roomGame = FileManager.getGameFiles().stream()
                .filter(gameFile -> gameFile.getRoomName().equals(roomName))
                .collect(Collectors.toList()).get(0);

        if (roomGame.isPlayersDataAccurate() && (playerName.equals(roomGame.getFirstPlayerName()) || playerName.equals(roomGame.getSecondPlayerName()))) {
            response.setStatus(201);
            return;
        }

        if (action.equals("add")) {
            if (roomGame.isWatcher(playerName)) {
                response.setStatus(203);
                return;
            }
            roomGame.addWatcherIfNotExists(playerName);
        } else {
            roomGame.removeWatcher(playerName);
        }

        response.setStatus(200);
    }
}
