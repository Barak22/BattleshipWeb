package api.servlets;

import api.components.GameRoom;
import api.enums.CookieTypes;
import api.managers.FileManager;
import api.managers.SessionManager;
import api.utils.CookieUtils;
import logic.TheGame;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@MultipartConfig
@WebServlet(name = "UploadFileServlet")
public class UploadFileServlet extends HttpServlet {

    private static final String UPLOADS_DIR_NAME = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cookieUserName = CookieUtils.getCookieValue(request.getCookies(), CookieTypes.USER_NAME);
        if (!SessionManager.isUserExists(cookieUserName)) {
            response.getWriter().print("You logged out");
            response.setStatus(501);
            return;
        }

        // Get file details
        String roomName = request.getParameter("fileName").replace(" ", "_");
        String username = request.getParameter("username");
        Part file = request.getPart("file");
        // Try to upload the file
        if (!FileManager.isGameFileExists(roomName)) {
            String partFilename = getPartFilename(file);
            InputStream content = file.getInputStream();
            // Reads content via buffer
            byte[] buffer = new byte[content.available()];
            content.read(buffer);
            // Prepare uploads directory
            File directory = new File(UPLOADS_DIR_NAME);
            if (!directory.exists()) {
                directory.mkdir();
            }
            File actualFile = new File(UPLOADS_DIR_NAME + "/" + partFilename);
            OutputStream outStream = new FileOutputStream(actualFile);
            outStream.write(buffer);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            try {
                placeFile(roomName, partFilename, actualFile, username);
                response.getWriter().write("<span id=\"status-success\">The Room '" + roomName + "' has been opened successfully!</span>");
            } catch (Exception e) {
                response.getWriter().write("<span id=\"status-fail\">File Error: " + e.getMessage() + "</span>");
            }
        } else {
            response.getWriter().write("<span id=\"status-fail\">Room name '" + roomName + "' is already exists!</span>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //-------------------------------------------------//
    // Creates and saves a GameRoom object
    //-------------------------------------------------//
    private static void placeFile(String roomName, String theName, File actualFile, String username) throws Exception {
        GameRoom gameRoom = new GameRoom(roomName, actualFile);
        TheGame gameManager = new TheGame();
        IInputVerifier inputVerifier = new XmlFileVerifier();
        ErrorCollector errorCollector = new ErrorCollector();
        if (!inputVerifier.isFileOk(UPLOADS_DIR_NAME + "/" + theName, errorCollector)
                || !gameManager.loadFile(UPLOADS_DIR_NAME + "/" + theName, errorCollector)) {
            if (!errorCollector.getMessages().isEmpty()) {
                StringBuilder errors = new StringBuilder();
                errorCollector.getMessages().forEach(errors::append);
                throw new Exception(errors.toString());
            }
        }

        gameRoom.setGameManager(gameManager);
        gameRoom.setAuthor(username);
        FileManager.addGameFile(gameRoom);
    }

    //-------------------------------------------------//
    // Extracts the part file name
    //-------------------------------------------------//

    private static String getPartFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }

        return null;
    }
}
