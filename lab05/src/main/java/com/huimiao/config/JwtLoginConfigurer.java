package com.huimiao.config;

import com.huimiao.filter.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private JwtAuthenticationFilter authFilter;

    public JwtLoginConfigurer(){
        this.authFilter = new JwtAuthenticationFilter();
    }

    @Override
    public void configure(B builder) {
        authFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        JwtAuthenticationFilter filter = postProcess(authFilter);

        builder.addFilterAfter(filter, LogoutFilter.class);
    }

    public JwtLoginConfigurer<T,B> setSuccessHandler(AuthenticationSuccessHandler successHandler){
        authFilter.setSuccessHandler(successHandler);
        return  this;
    }
}
