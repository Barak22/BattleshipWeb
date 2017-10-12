package api.components;

import logic.TheGame;
import logic.exceptions.XmlContentException;

import java.io.File;

public class GameRoom {

    private final String[] players;
    private String author;
    private String roomName;
    private File file;
    private TheGame gameManager;
    private int numOfPlayers;
    private String lastPlayMsg;
    private boolean isReadyToHardReset;

    public GameRoom(String roomName, File file) {
        this.roomName = roomName;
        this.file = file;
        numOfPlayers = 0;
        players = new String[]{null, null};
        lastPlayMsg = "- Waiting for the first move -";
        isReadyToHardReset = false;
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
        return numOfPlayers == 0 ? numOfPlayers : --numOfPlayers;
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
        return players[gameManager.getCurrentPlayerIndex()];
    }

    public boolean isPlayerAlreadyIn(String playerName) {
        return playerName.equalsIgnoreCase(players[0]) || playerName.equalsIgnoreCase(players[1]);
    }

    public String getLastPlayMsg() {
        return lastPlayMsg;
    }

    public void setLastPlayMsg(String lastPlayMsg) {
        this.lastPlayMsg = lastPlayMsg;
    }

    public String getSecondPlayerName() {
        return players[1];
    }

    public String getFirstPlayerName() {
        return players[0];
    }

    public boolean isMyTurn(String userName) {
        return getCurrentPlayerName().equalsIgnoreCase(userName);
    }

    public boolean isPlayersDataAccurate() {
        return !isReadyToHardReset;
    }

    public void reset() {
        numOfPlayers = 0;
        isReadyToHardReset = true;
    }

    public void hardReset() {
        if (isReadyToHardReset) {
            isReadyToHardReset = false;
            numOfPlayers = 1;
            players[0] = null;
            players[1] = null;
            lastPlayMsg = "- Waiting for the first move -";
            try {
                getGameManager().resetGame();
            } catch (XmlContentException e) {
                e.printStackTrace();
            }
        }
    }
}
