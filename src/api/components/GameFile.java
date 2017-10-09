package api.components;

import logic.TheGame;

import java.io.File;

public class GameFile {

    private String author;
    private String roomName;
    private File file;
    private TheGame gameManager;

    public GameFile(String roomName, File file) {
        this.roomName = roomName;
        this.file = file;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String fileName) {
        this.roomName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public TheGame getGameManager() {
        return gameManager;
    }

    public void setGameManager(TheGame gameManager) {
        this.gameManager = gameManager;
    }
}
