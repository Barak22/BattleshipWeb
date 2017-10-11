package api.components;

import logic.TheGame;

import java.io.File;

public class GameRoom {

    private String author;
    private String roomName;
    private File file;
    private TheGame gameManager;
    private int numOfPlayers;
    private String[] players;

    public GameRoom(String roomName, File file) {
        this.roomName = roomName;
        this.file = file;
        numOfPlayers = 0;
        players = new String[]{null, null};
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
        roomName = fileName;
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

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int incrementAndGet() {
        return ++numOfPlayers;
    }

    public int decrementAndGet() {
        return --numOfPlayers;
    }

    public void setPlayerName(String playerName) {
        if (players[0] != null) {
            players[1] = playerName;
        } else {
            players[0] = playerName;
        }
    }

    public void removeFirstPlayerName() {
        players[0] = null;
    }

    public String getCurrentPlayerName() {
        return gameManager.getCurrentPlayerName();
    }

    public boolean isPlayerAlreadyIn(String playerName) {
        return playerName.equalsIgnoreCase(players[0]) || playerName.equalsIgnoreCase(players[1]);
    }
}
