package api.servlets;

import api.components.GameFile;
import api.managers.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "UploadFileServlet")
public class UploadFileServlet extends HttpServlet {

    private static final String UPLOADS_DIR_NAME = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get file details
        Part file = request.getPart("file");
        String fileName = request.getParameter("fileName");
        String username = request.getParameter("username");
        // Try to upload the file
        if (!FileManager.isGameFileExists(fileName)) {
//            filename = getFilename(file);
            InputStream content = file.getInputStream();

            byte[] buffer = new byte[content.available()];
            content.read(buffer);

            File directory = new File(UPLOADS_DIR_NAME);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File actualFile = new File(UPLOADS_DIR_NAME + "/" + fileName);
            OutputStream outStream = new FileOutputStream(actualFile);
            outStream.write(buffer);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            try {
                placeFile(fileName, actualFile, username);
                response.getWriter().write("<span style=\"color:green;\">File " + fileName + " file has been uploaded successfully!</span>");
            } catch (Exception e) {
                response.getWriter().write("<span style=\"color:red;\">" + e.getMessage() + "</span>");
            }
        } else {
            response.getWriter().write("Game File is Already Exist!");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static void placeFile(String fileName, File actualFile, String username) {
        GameFile gameFile = new GameFile(fileName, actualFile);
        FileManager.addGameFile(gameFile);
        gameFile.setAuthor(username);
    }
}
