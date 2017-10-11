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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("room");
        GameRoom theRoom = FileManager.getRoomByName(roomName);
        PrintWriter out = response.getWriter();
        if (theRoom.getGameManager().isGameOn()) {
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
        out.write("<span class=\"stats-attriubte\">Score: </span>" + player.getScore());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attriubte\">Mines: </span>" + player.getMines());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attriubte\">Avg. Time: </span>" + player.averageTime());
        out.write("</div>");
        out.write("</div>");

        out.write("<div class=\"row\">");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attriubte\">Hits: </span>" + player.getHits());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attriubte\">Misses: </span>" + player.getMisses());
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<span class=\"stats-attriubte\">Turns: </span>" + player.getTurns());
        out.write("</div>");
        out.write("</div>");

        out.write("</div>");
    }
}
