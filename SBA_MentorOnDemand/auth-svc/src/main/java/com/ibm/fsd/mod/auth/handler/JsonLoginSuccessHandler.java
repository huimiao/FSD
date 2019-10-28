package com.ibm.fsd.mod.auth.handler;

import com.ibm.fsd.mod.common.auth.Sessions;
import com.ibm.fsd.mod.common.props.AppProps;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final AppProps appProps;

    public JsonLoginSuccessHandler(AppProps appProps) {
        this.appProps = appProps;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = "";
        if (authentication.getAuthorities().size() != 0) {
            role = ((SimpleGrantedAuthority) (authentication.getAuthorities().toArray()[0])).getAuthority();
        }

        Sessions.loginUser(username, role, true, appProps.getSigningSecret(), appProps.getDomain(), response);
    }
}
