package api.managers;

import api.components.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by barakm on 07/10/2017
 */
public final class SessionManager {

    private static final Map<String, User> USERS = new HashMap<>();

    private SessionManager() {
    }

    public static User addUser(User user) {
        boolean isUserExist = USERS.keySet()
                                   .stream()
                                   .anyMatch(name -> name.equals(user.getNameForMapKey()));

        return isUserExist ? USERS.get(user.getNameForMapKey()) : USERS.put(user.getNameForMapKey(), user);
    }

    public static boolean removeUser(String userName) {
        return USERS.remove(userName.toLowerCase(), USERS.get(userName.toLowerCase()));
    }

    public static Collection<User> getUsers() {
        return USERS.values();
    }

    public static boolean isUserExists(String userName) {
        return USERS.containsKey(userName.toLowerCase());
    }
}
