package com.huimiao.config;

import com.huimiao.filter.JsonUsernamePasswordAuthenticationFilter;
import com.huimiao.handler.JsonLogonFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private JsonUsernamePasswordAuthenticationFilter authFilter;

    public JsonLoginConfigurer() {
     this.authFilter = new JsonUsernamePasswordAuthenticationFilter();
    }

    @Override
    public void configure(B builder) {
        authFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        authFilter.setAuthenticationFailureHandler(new JsonLogonFailureHandler());

        JsonUsernamePasswordAuthenticationFilter filter = postProcess(authFilter);

        builder.addFilterAfter(filter, LogoutFilter.class);
    }

    public JsonLoginConfigurer<T, B>  loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
        this.authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }
}
