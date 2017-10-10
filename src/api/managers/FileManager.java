package api.managers;

import api.components.GameFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class FileManager {

    private static final Map<String, GameFile> GAME_FILES = new HashMap<>();

    private FileManager() {
    }

    public static boolean isGameFileExists(String gameFile) {
        return GAME_FILES.containsKey(gameFile);
    }

    public static GameFile addGameFile(GameFile gameFile) {
        return GAME_FILES.putIfAbsent(gameFile.getRoomName(), gameFile);
    }

    public static boolean removeGameFile(String gameFile) {
        //        User user = USERS.get(userName);
        return GAME_FILES.remove(gameFile, GAME_FILES.get(gameFile));
    }

    public static Collection<GameFile> getGameFiles() {
        return GAME_FILES.values();
    }

    public static GameFile getRoomByName(String roomName) {
        return GAME_FILES.get(roomName);
    }
}
