package com.ibm.fsd.mod.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.fsd.mod.common.crypto.Sign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Sessions {
    public static final long SHORT_SESSION = TimeUnit.HOURS.toMillis(12);
    public static final long LONG_SESSION = TimeUnit.HOURS.toMillis(30 * 24 * 365);

    public static void loginUser(String username,
                                 String role,
                                 boolean rememberMe,
                                 String signingSecret,
                                 String externalApex,
                                 HttpServletResponse response) {
        long duration;

        if (rememberMe) {
            // "Remember me"
            duration = LONG_SESSION;
        } else {
            duration = SHORT_SESSION;
        }

        String token = Sign.generateSessionToken(username, signingSecret, role, duration);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<>();
        map.put("current_user", username);
        map.put("current_user_role", role);
        map.put("token", token);

        try {
            String outputJson = mapper.writeValueAsString(map);
            response.getWriter().write(outputJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer")) {
            return null;
        } else {
            return authorization.substring(7);
        }
    }
}

