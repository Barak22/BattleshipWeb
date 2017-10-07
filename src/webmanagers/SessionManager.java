package webmanagers;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by barakm on 07/10/2017
 */
public final class SessionManager {

    private static final Set<String> NAMES = new HashSet<>();

    private SessionManager() {
    }

    public static boolean addUser(String userName) {
        return NAMES.add(userName);
    }

    public static boolean removeUser(String userName) {
        return NAMES.remove(userName);
    }

    public static Set<String> getUsers() {
        return NAMES;
    }
}
