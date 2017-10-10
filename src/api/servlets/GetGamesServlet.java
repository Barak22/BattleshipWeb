package api.servlets;

import api.components.GameFile;
import api.managers.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetGamesServlet")
public class GetGamesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if (!FileManager.getGameFiles().isEmpty()) {
            int row = 1;

            out.write("<table class=\"table table-active\">");
            out.write("<thead>");
            out.write("<tr>");
            out.write("<th>#</th>");
            out.write("<th>Name</th>");
            out.write("<th>Type</th>");
            out.write("<th>Size</th>");
            out.write("<th>Author</th>");
            out.write("<th>Players</th>");
            out.write("<th>Status</th>");
            out.write("</tr>");
            out.write("</thead>");
            out.write("<tbody>");

            for (GameFile gameFile : FileManager.getGameFiles()) {
                out.write("<tr>");
                out.write("<th scope=\"row\">" + row + "</th>");
                out.write("<td>" + gameFile.getRoomName() + "</td>");
                out.write("<td>" + gameFile.getGameManager().getGameType() + "</td>");
                out.write("<td>" + gameFile.getGameManager().getBoardSize() + "</td>");
                out.write("<td>" + gameFile.getAuthor() + "</td>");
                out.write("<td>" + 0 + "</td>");
                out.write("<td>" + "Active" + "</td>");
                out.write("</tr>");
                row++;
            }
            out.write("</tbody>");
            out.write("</table>");
        } else {
            out.write("There are no available rooms to show");
        }
    }
}
