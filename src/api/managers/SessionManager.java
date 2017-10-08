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
        return USERS.putIfAbsent(user.getName(), user);
    }

    public static boolean removeUser(String userName) {
        //        User user = USERS.get(userName);
        return USERS.remove(userName, USERS.get(userName));
    }

    public static Collection<User> getUsers() {
        return USERS.values();
    }
}
