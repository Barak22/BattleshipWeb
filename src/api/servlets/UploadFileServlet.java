package api.servlets;

import api.components.GameFile;
import api.managers.FileManager;
import core.logic.TheGame;
import core.ui.verifiers.ErrorCollector;
import core.ui.verifiers.IInputVerifier;
import core.ui.verifiers.XmlFileVerifier;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get file details
        String fileName = request.getParameter("fileName");
        String username = request.getParameter("username");
        Part file = request.getPart("file");
        // Try to upload the file
        if (!FileManager.isGameFileExists(fileName)) {
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
                placeFile(fileName, actualFile, username);
                response.getWriter().write("<span style=\"color:green;\">File " + fileName + " file has been uploaded successfully!</span>");
            } catch (Exception e) {
                response.getWriter().write(e.getMessage());
                response.setStatus(500);
            }
        } else {
            response.getWriter().write("Game File is Already Exist!");
            response.setStatus(500);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //-------------------------------------------------//
    // Creates and saves a GameFile object
    //-------------------------------------------------//
    private static void placeFile(String fileName, File actualFile, String username) throws Exception {
        GameFile gameFile = new GameFile(fileName, actualFile);
        String filePath = (UPLOADS_DIR_NAME + "\\" + fileName);
        TheGame gameManager = new TheGame();
        IInputVerifier inputVerifier = new XmlFileVerifier();
        ErrorCollector errorCollector = new ErrorCollector();

        if (!inputVerifier.isFileOk(filePath, errorCollector) || !gameManager.loadFile(filePath, errorCollector)) {
            if (!errorCollector.getMessages().isEmpty()) {
                StringBuilder errors = new StringBuilder();
                errorCollector.getMessages().forEach(errors::append);
                throw new Exception(errors.toString());
            }


            FileManager.addGameFile(gameFile);
            gameFile.setGameManager(gameManager);
            gameFile.setAuthor(username);
        }
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
