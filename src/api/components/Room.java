package api.components;

import logic.enums.GameType;

/**
 * Created by barakm on 08/10/2017
 */
public class Room {

    private final String name;
    private final String ownerName;
    private final int boardSize;
    private final GameType gameType;
    private boolean isTherePlayer;
    private boolean isGameStarted;

    public Room(String name, String ownerName, int boardSize, GameType gameType) {
        this.name = name;
        this.ownerName = ownerName;
        this.boardSize = boardSize;
        this.gameType = gameType;
        isTherePlayer = false;
        isGameStarted = false;
    }
}
