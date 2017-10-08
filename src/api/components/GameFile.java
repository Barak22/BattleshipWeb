package api.components;

import java.io.File;

public class GameFile {

    private String author;
    private String fileName;
    private File file;

    public GameFile(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
