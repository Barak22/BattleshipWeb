package api.enums;

/**
 * Created by barakm on 08/10/2017
 */
public enum CookieTypes {
    USER_NAME("battleshipUserName");

    private final String value;

    CookieTypes(String battleshipUserName) {
        value = battleshipUserName;
    }

    public String getValue() {
        return value;
    }

}
