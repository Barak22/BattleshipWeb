package api.servlets;

import api.components.GameRoom;
import api.managers.FileManager;
import logic.TheGame;
import logic.exceptions.XmlContentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetBoardsServlet")
public class GetBoardsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("room");
        GameRoom theRoom = FileManager.getRoomByName(roomName);
        PrintWriter out = response.getWriter();
        if (theRoom != null) {
            if (theRoom.getNumOfPlayers() == 1) {
                buildWaitingMessage(out);
                response.setStatus(201);
                return;
            }

            try {
                theRoom.getGameManager().startGame();
            } catch (XmlContentException e) {
                out.write(e.getMessage());
                response.setStatus(500);
            }
            buildBoardsHTML(out, theRoom);
            response.setStatus(200);
        } else {
            out.write("Sorry, the room is no longer exists"); // NOT SHOULD HAPPEN IN A VALID GAME LINE
            response.setStatus(500);
        }
    }

    private void buildBoardsHTML(PrintWriter out, GameRoom theRoom) {
        TheGame gameManager = theRoom.getGameManager();

        out.write("<div class=\"row\">");
        buildBoardFromMatrix(out, gameManager.getOpponentBoardToPrint(), true);
        if (gameManager.getBoardSize() > 11) {
            out.write("</div>");
            out.write("<div class=\"row\">");
        }
        buildBoardFromMatrix(out, gameManager.getCurrentPlayerBoardToPrint(), false);
        out.write("</div>");
    }

    private void buildBoardFromMatrix(PrintWriter out, char[][] board, boolean isClickable) {
        out.write("<div class=\"col-lg-6\">");
        out.write("<table class=\"table table-bordered\">");

        // Column characters
        out.write("<thead>");
        out.write("<tr>");
        out.write("<th>#</th>");
        char colVal = 'A';
        for (int i = 1; i < board.length; i++) {
            out.write("<th>" + colVal + "</th>");
            colVal++;
        }
        out.write("</tr>");
        out.write("</thead>");

        // Board buttons
        out.write("<tbody>");
        for (int i = 1; i < board.length; i++) {
            out.write("<tr>");
            out.write("<th scope=\"row\">" + i + "</th>");
            for (int j = 1; j < board.length; j++) {
                if (isClickable) {
                    // set play button
                    out.print("<td><button class=\"btn btn-board btn-default\">" + board[i][j] + "</button></td>");
                } else {
                    // set button with drag & drop
                    out.print("<td><button class=\"btn btn-board btn-default\">" + board[i][j] + "</button></td>");
                }
            }
            out.write("</tr>");
        }
        out.write("</tbody>");

        out.write("</table>");
        out.write("</div>");
    }

    private void buildWaitingMessage(PrintWriter out) {
        out.write("<div class=\"row\">");
        out.write("<div class=\"col-lg-4\">");
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("<h3 id=\"waiting-other-player\">Waiting for other player to join...</h3>");
        out.write("<div class=\"loader\"></div>");
        out.write("</div>");
        out.write("<div class=\"col-lg-4\">");
        out.write("</div>");
        out.write("</div>");
    }
}
