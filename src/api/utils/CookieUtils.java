package api.utils;

import api.enums.CookieTypes;

import javax.servlet.http.Cookie;

/**
 * Created by barakm on 11/10/2017
 */
public final class CookieUtils {

    private CookieUtils() {
    }

    public static String getCookieValue(Cookie[] cookies, CookieTypes cookieType) {
        String cookieValue = "";

        if (cookies == null) {
            cookies = new Cookie[0];
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieType.getValue())) {
                cookieValue = cookie.getValue();
            }
        }

        return cookieValue;
    }
}
