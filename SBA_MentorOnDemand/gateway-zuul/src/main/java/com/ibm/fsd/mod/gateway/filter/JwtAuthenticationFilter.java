package com.ibm.fsd.mod.gateway.filter;

import com.ibm.fsd.mod.auth.client.AuthClient;
import com.ibm.fsd.mod.auth.dto.GenericAuthResponse;
import com.ibm.fsd.mod.auth.dto.TokenUserDto;
import com.ibm.fsd.mod.common.auth.Sessions;
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

        if (!StringUtils.isEmpty(token)) {
            GenericAuthResponse genericAuthResponse = authClient.verifyJwtToken(token);

            if (genericAuthResponse.isSuccess()) {
                TokenUserDto tokenUserDto = genericAuthResponse.getTokenUserDto();
                log.info(tokenUserDto.toString());
                return null;
            } else {
                log.warn("Token validation failed");
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseBody(genericAuthResponse.getMessage());
                currentContext.setResponseStatusCode(401);
            }
        }

        return null;
    }
}
