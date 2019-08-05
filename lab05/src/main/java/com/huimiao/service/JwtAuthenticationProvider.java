package com.huimiao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.huimiao.dto.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import java.util.Calendar;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();

        if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
            throw new NonceExpiredException("Token expires");
        }
        String userName = jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (userDetails == null || userDetails.getPassword() == null) {
            throw new NonceExpiredException("Token expires");
        }
        String encryptSalt = userDetails.getPassword();

        try {
            Algorithm algorithm = Algorithm.HMAC256(encryptSalt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(userName)
                    .build();
            verifier.verify(jwt.getToken());
        } catch (Exception e) {
            throw new BadCredentialsException("JWT token verify fail", e);
        }

        JwtAuthenticationToken token = new JwtAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
