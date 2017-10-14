package api.servlets;

import api.components.GameRoom;
import api.managers.FileManager;
import logic.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.SortedMap;

import static java.lang.System.lineSeparator;

@WebServlet(name = "GetMatchDetailsServlet")
public class GetMatchDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("room");
        GameRoom theRoom = FileManager.getRoomByName(roomName);
        PrintWriter out = response.getWriter();
        if (!theRoom.isGameEnded() && theRoom.getGameManager().isPlayerWon()) {
            theRoom.setGameEnded(true);
            out.write("<div id=\"chatEnded\"></div>");
            theRoom.addMessage("Chat is ended", "System");
        }

        theRoom.getChatMessages().forEach(message -> {
            out.write("<div class=\"media msg \">");
            out.write("<div class=\"media-body\">");
            out.write("<medium class=\"pull-right time\">" + message.getRight() + "</medium>");
            out.write("<h4 class=\"media-heading\">" + message.getLeft() + "</h4>");
            if (message.getLeft().equalsIgnoreCase("System")) {
                out.write("<medium class=\"col-lg-10 red\">" + message.getMiddle() + "</medium>");
            } else {
                out.write("<medium class=\"col-lg-10\">" + message.getMiddle() + "</medium>");
            }
            out.write("</div>");
            out.write("</div>");
        });
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("room");
        GameRoom theRoom = FileManager.getRoomByName(roomName);
        PrintWriter out = response.getWriter();
        if (theRoom.getGameManager().isGameOn()) {
            buildWatchersList(out, theRoom);
            buildGeneralMatchDetails(out, theRoom);
            out.write("<div class=\"row\">");
            buildPlayerStats(out, theRoom.getGameManager().getFirstPayer(), theRoom.getFirstPlayerName(), theRoom.getGameManager().getFirstPlayerBattleships());
            buildPlayerStats(out, theRoom.getGameManager().getSecondPayer(), theRoom.getSecondPlayerName(), theRoom.getGameManager().getSecondPlayerBattleships());
            out.write("</div>");
            buildLastMoveMsg(out, theRoom);
            response.setStatus(200);
        } else if (theRoom.getGameManager().isPlayerWon()) {
            buildGameOverMatchDetails(out, theRoom);
            out.write("<div class=\"row\">");
            buildPlayerStats(out, theRoom.getGameManager().getFirstPayer(), theRoom.getFirstPlayerName(), theRoom.getGameManager().getFirstPlayerBattleships());
            buildPlayerStats(out, theRoom.getGameManager().getSecondPayer(), theRoom.getSecondPlayerName(), theRoom.getGameManager().getSecondPlayerBattleships());
            out.write("</div>");
            buildLastMoveMsg(out, theRoom);
            response.setStatus(202);
        } else {
            // do nothing
            response.setStatus(201);
        }
    }

    private void buildGeneralMatchDetails(PrintWriter out, GameRoom gameRoom) {
        out.write("<div class=\"row\" id=\"gameLastMove\">");
        out.write("<div class=\"col-lg-12\">");
        out.write("<h4>Total Time: " + gameRoom.getGameManager().getTotalTime() + "</h4>");
        out.write("<h4>Played Turns: " + gameRoom.getGameManager().getTotalTurns() + "</h4>");
        out.write("<h4>Game Type: " + gameRoom.getGameManager().getGameType() + "</h4>");
        out.write("</div>");
        out.write("</div>");
        out.write("<hr>");
    }

    private void buildGameOverMatchDetails(PrintWriter out, GameRoom gameRoom) {
        out.write("<div class=\"row\" id=\"gameOver\">");
        out.write("<div class=\"col-lg-12\">");
        out.write("<h4>Total Time: " + gameRoom.getGameManager().getTotalTime() + "</h4>");
        out.write("<h4>Played Turns: " + gameRoom.getGameManager().getTotalTurns() + "</h4>");
        out.write("<h4>Game Type: " + gameRoom.getGameManager().getGameType() + "</h4>");
        out.write("</div>");
        out.write("</div>");
        out.write("<hr>");
    }

    private void buildLastMoveMsg(PrintWriter out, GameRoom gameRoom) {
        out.write("<hr>");
        out.write("<div class=\"row\" id=\"gameLastMove\">");
        out.write("<div class=\"col-lg-12\">");
        out.write("<h2>Last Played Move:</h2>");
        out.write(gameRoom.getLastPlayMsg());
        out.write("</div>");
        out.write("</div>");
        out.write("<hr>");
    }

    private void buildPlayerStats(PrintWriter out, Player player, String playerName, SortedMap<Integer, Integer> shipsType) {
        out.write("<div class=\"col-lg-6\">");
        out.write("<div class=\"row\">");
        out.write("<div class=\"col-lg-12\">");
        out.write("<div class=\"hdl-stats\" id=\"details-first-player\">" + playerName + "</div>");
        out.write("</div>");
        out.write("</div>");

        out.write("<div class=\"row\">");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attribute\">Score: </span>" + player.getScore());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attribute\">Mines: </span>" + player.getMines());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attribute\">Avg. Time: </span>" + player.averageTime());
        out.write("</div>");
        out.write("</div>");

        out.write("<div class=\"row\">");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attribute\">Hits: </span>" + player.getHits());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attribute\">Misses: </span>" + player.getMisses());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attribute\">Turns: </span>" + player.getTurns());
        out.write("</div>");
        out.write("</div>");

        out.write("<div class=\"row\">");
        out.write("<div class=\"col-lg-12\">");
        StringBuilder stringBuilder = new StringBuilder();
        appendBattleshipsList(stringBuilder, shipsType);
        out.write(stringBuilder.toString());
        out.write("</div>");
        out.write("</div>");

        out.write("</div>");
    }

    private void appendBattleshipsList(StringBuilder stringBuilder, SortedMap<Integer, Integer> shipsType) {
        stringBuilder.append(lineSeparator());
        stringBuilder.append("<ul>");
        shipsType.forEach((k, v) -> stringBuilder.append("<li>")
                .append("Length Type: ")
                .append(k)
                .append("  Amount Left:")
                .append(v)
                .append("</li>"));
        stringBuilder.append("</ul>");
    }

    private void buildWatchersList(PrintWriter out, GameRoom gameRoom) {
        out.write("<h4 id=\"hdl-watchers\">Watchers</h4>");
        out.write("<div class=\"content-watchers\">");
        if (gameRoom.getWatchersAmount() != 0) {
            HashSet<String> watchers = gameRoom.getWatchers();
            for (String watcherName : watchers) {
                out.write("<button type=\"button\" class=\"list-group-item list-group-item-action content-watchers\">");
                out.write(watcherName);
                out.write("</button>");
            }
        } else {
            out.write("Nobody is watching this game at the moment");
        }
        out.write("</div>");
        out.write("<hr>");
    }
}
