package com.huimiao.lab06.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.huimiao.lab06.common.Constants.CAPTCHA_VALUE;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    public LoginFilter() {
        super(new RegexRequestMatcher("(/login)", "POST"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!requiresAuthentication(req, res)) {
            chain.doFilter(request, response);
            return;
        }

        String captcha = (String) (req.getSession().getAttribute(CAPTCHA_VALUE));
        if (captcha != null && captcha.equalsIgnoreCase(request.getParameter("captcha"))) {
            chain.doFilter(request, response);
        } else {
            unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Incorrect captcha code."));
            return;
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
