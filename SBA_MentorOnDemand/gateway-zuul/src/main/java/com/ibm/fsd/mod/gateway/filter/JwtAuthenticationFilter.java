package com.ibm.fsd.mod.gateway.filter;

import com.ibm.fsd.mod.auth.client.AuthClient;
import com.ibm.fsd.mod.auth.dto.GenericAuthResponse;
import com.ibm.fsd.mod.auth.dto.TokenUserDto;
import com.ibm.fsd.mod.common.auth.Sessions;
import com.ibm.fsd.mod.gateway.config.ModRequestMatcherList;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class JwtAuthenticationFilter extends ZuulFilter {
    @Autowired
    private AuthClient authClient;

    @Autowired
    private ModRequestMatcherList modRequestMatcherList;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = Sessions.getToken(request);


        log.info(request.getRequestURI());

        if (!StringUtils.isEmpty(token)) {
            GenericAuthResponse genericAuthResponse = authClient.verifyJwtToken(token);

            if (genericAuthResponse.isSuccess()) {
                TokenUserDto tokenUserDto = genericAuthResponse.getTokenUserDto();
                log.info(tokenUserDto.toString());
                if(!validAccess(request, tokenUserDto.getRole())){
                    log.warn("illegal access with correct token");
                    currentContext.setSendZuulResponse(false);
                    currentContext.setResponseBody("illegal access");
                    currentContext.setResponseStatusCode(401);
                }
                return null;
            } else {
                log.warn("Token validation failed");
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseBody(genericAuthResponse.getMessage());
                currentContext.setResponseStatusCode(401);
            }
        } else {
            if (!validAccess(request)) {
                log.warn("illegal access without token");
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseBody("illegal access");
                currentContext.setResponseStatusCode(401);
            }
        }

        return null;
    }

    private boolean validAccess(HttpServletRequest request, String role) {
        if (role == null || !(role.equals("ROLE_USER") || role.equals("ROLE_MENTOR"))) {
            if (modRequestMatcherList.getAll().stream().anyMatch(m -> m.matches(request))) {
                return false;
            }
        }

        if (role.equals("ROLE_USER") && modRequestMatcherList.getMentor().stream().anyMatch(m -> m.matches(request))) {
            return false;
        }

        return true;
    }

    private boolean validAccess(HttpServletRequest request) {
        if (modRequestMatcherList.getAll().stream().anyMatch(m -> m.matches(request))) {
            return false;
        }

        return true;
    }
}
