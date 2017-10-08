package api.components;

import api.enums.WebStatus;

/**
 * Created by barakm on 08/10/2017
 */
public class User {

    private final String name;
    private WebStatus status;

    public User(String name, WebStatus status) {
        this.name = name;
        this.status = status;
    }

    public WebStatus getStatus() {
        return status;
    }

    public void setStatus(WebStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
}
