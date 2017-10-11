package api.managers;

import api.components.GameRoom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class FileManager {

    private static final Map<String, GameRoom> GAME_FILES = new HashMap<>();

    private FileManager() {
    }

    public static boolean isGameFileExists(String gameFile) {
        return GAME_FILES.containsKey(gameFile);
    }

    public static GameRoom addGameFile(GameRoom gameRoom) {
        return GAME_FILES.putIfAbsent(gameRoom.getRoomName(), gameRoom);
    }

    public static GameRoom removeGameFile(String gameFile) {
        return GAME_FILES.remove(gameFile);
    }

    public static Collection<GameRoom> getGameFiles() {
        return GAME_FILES.values();
    }

    public static GameRoom getRoomByName(String roomName) {
        return GAME_FILES.get(roomName);
    }
}
