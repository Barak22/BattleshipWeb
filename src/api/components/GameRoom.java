package api.components;

import api.utils.Triple;
import logic.TheGame;
import logic.exceptions.XmlContentException;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameRoom {

    private final String[] players;
    private final List<Triple<String, String, String>> chatMessages;
    private String author;
    private String roomName;
    private File file;
    private TheGame gameManager;
    private int numOfPlayers;
    private HashSet<String> watchers;
    private String lastPlayMsg;
    private boolean isReadyToHardReset;
    private String winnerName;
    private boolean endMessagePrinted;

    public GameRoom(String roomName, File file) {
        this.roomName = roomName;
        this.file = file;
        numOfPlayers = 0;
        watchers = new HashSet<>();
        players = new String[]{"", ""};
        lastPlayMsg = "- Waiting for the first move -";
        isReadyToHardReset = false;
        endMessagePrinted = false;
        winnerName = "";
        chatMessages = new ArrayList<>();
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

    public void setPlayerName(String playerName) {
        if (!players[0].isEmpty()) {
            players[1] = playerName;
        } else {
            players[0] = playerName;
        }
    }

    public void removeUserName(String playerName) {
        if (players[0].equalsIgnoreCase(playerName)) {
            players[0] = "";
            if (numOfPlayers > 0) {
                --numOfPlayers;
            }
        } else if (players[1].equalsIgnoreCase(playerName)) {
            players[1] = "";
            if (numOfPlayers > 0) {
                --numOfPlayers;
            }
        }
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

    public boolean isPlayersDataAccurate() {
        return !isReadyToHardReset;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public void reset() {
        numOfPlayers = 0;
        isReadyToHardReset = true;
    }

    public void hardReset() {
        if (isReadyToHardReset) {
            isReadyToHardReset = false;
            endMessagePrinted = false;
            numOfPlayers = 1;
            players[0] = "";
            players[1] = "";
            lastPlayMsg = "- Waiting for the first move -";
            winnerName = "";
            chatMessages.clear();
            try {
                getGameManager().resetGame();
            } catch (XmlContentException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMessage(String message, String userName) {
        synchronized (this) {
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Triple<String, String, String> messageList = new Triple<>(userName, message, time);
            chatMessages.add(messageList);
        }
    }

    public List<Triple<String, String, String>> getChatMessages() {
        return chatMessages;
    }

    public int getWatchersAmount() {
        return watchers.size();
    }

    public void addWatcherIfNotExists(String username) {
        watchers.add(username);
    }

    public void removeWatcher(String username) {
        watchers.remove(username);
    }

    public void addFinalMessage(String message, String userName) {
        if (!endMessagePrinted) {
            synchronized (this) {
                if (!endMessagePrinted) {
                    endMessagePrinted = true;
                    addMessage(message, userName);
                }
            }
        }
    }

    public HashSet<String> getWatchers() {
        return watchers;
    }

    public boolean isWatcher(String username) {
        return watchers.contains(username);
    }
}
