package com.huimiao.lab06.config;

import com.huimiao.lab06.filter.LoginFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilterConfigurer<T extends LoginFilterConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private LoginFilter loginFilter;

    public LoginFilterConfigurer() {
        this.loginFilter = new LoginFilter();
    }

    @Override
    public void configure(B builder) {
        loginFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        loginFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        LoginFilter filter = postProcess(loginFilter);

        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
