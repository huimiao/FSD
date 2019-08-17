package com.huimiao.lab06.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.huimiao.lab06.common.Constants.CAPTCHA_VALUE;

public class CaptchaFilter extends OncePerRequestFilter implements InitializingBean {
    private RequestMatcher requiresAuthenticationRequestMatcher;
    @Getter
    @Setter
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(shouldNotFilter(request)){
            filterChain.doFilter(request, response);
        }

        String captcha = (String) (request.getSession().getAttribute(CAPTCHA_VALUE));
        request.getSession().removeAttribute(CAPTCHA_VALUE);
        if (captcha != null && captcha.equalsIgnoreCase(request.getParameter("captcha"))) {
            filterChain.doFilter(request, response);
        } else {
            failureHandler.onAuthenticationFailure(request,response, new InsufficientAuthenticationException("Incorrect captcha value"));
            return;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !requiresAuthenticationRequestMatcher.matches(request);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        this.requiresAuthenticationRequestMatcher = new RegexRequestMatcher("(/register.*)", "POST");
    }
}
