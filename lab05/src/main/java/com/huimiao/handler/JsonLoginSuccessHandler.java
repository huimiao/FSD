package com.huimiao.handler;

import com.huimiao.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.huimiao.common.Constants.JWT_TOKEN_NAME;

public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    public JsonLoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = userService.generateJwtToken((UserDetails) authentication.getPrincipal());
        Cookie cookie = new Cookie(JWT_TOKEN_NAME, token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
