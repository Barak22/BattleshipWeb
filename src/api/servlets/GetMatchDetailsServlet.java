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

@WebServlet(name = "GetMatchDetailsServlet")
public class GetMatchDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("room");
        GameRoom theRoom = FileManager.getRoomByName(roomName);
        PrintWriter out = response.getWriter();
        if (theRoom.getGameManager().isGameOn()) {
            buildGeneralMatchDetails(out, theRoom);
            out.write("<div class=\"row\">");
            buildPlayerStats(out, theRoom.getGameManager().getFirstPayer(), theRoom.getFirstPlayerName());
            buildPlayerStats(out, theRoom.getGameManager().getSecondPayer(), theRoom.getSecondPlayerName());
            out.write("</div>");
            buildLastMoveMsg(out, theRoom);
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

    private void buildPlayerStats(PrintWriter out, Player player, String playerName) {
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

        out.write("</div>");
    }
}
