package com.huimiao.filter;

import com.auth0.jwt.JWT;
import com.huimiao.dto.JwtAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.huimiao.common.Constants.JWT_TOKEN_NAME;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Getter
    @Setter
    private AuthenticationManager authenticationManager;
    @Setter
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final boolean debug = this.logger.isDebugEnabled();
        log.info("In JwtAuthenticationFilter");
        if (!requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authResult = null;
        AuthenticationException failed = null;
        String token = getJwtToken(request);

        try {
            if (StringUtils.hasText(token)) {
                if (debug) {
                    log.debug("JWT token found: "
                            + token + "");
                }

                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(JWT.decode(token));
                authResult = this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
            } else {
                failed = new InsufficientAuthenticationException("JWT is Empty");
            }
        } catch (AuthenticationException e) {
            failed = e;
        }

        if (authResult != null) {
            successfulAuthentication(request, response, authResult);
        } else {
            onUnsuccessfulAuthentication(request, response, failed);
        }

        filterChain.doFilter(request,response);
    }

    /**
     * Check if the cookie named #JWT_TOKEN_NAME in the request
     *
     * @param request
     * @return true if th cookie exist otherwise false
     */
    protected boolean requiresAuthentication(HttpServletRequest request) {
        if(request.getRequestURI().equalsIgnoreCase("/logout")){
            return false;
        }

        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JWT_TOKEN_NAME)) {
                return true;
            }
        }

        return false;
    }

    protected String getJwtToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JWT_TOKEN_NAME)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authResult)
            throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
