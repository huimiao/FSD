package com.ibm.fsd.mod.auth.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ibm.fsd.mod.auth.dto.GenericAuthResponse;
import com.ibm.fsd.mod.auth.dto.TokenUserDto;
import com.ibm.fsd.mod.common.api.ResultCode;
import com.ibm.fsd.mod.common.crypto.Sign;
import com.ibm.fsd.mod.common.props.AppProps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class AuthController {
    @Autowired
    private AppProps appProps;

    @PostMapping("/jwt/{token}")
    public GenericAuthResponse verifyJwtToken(@PathVariable String token) {
        GenericAuthResponse genericAuthResponse = new GenericAuthResponse();
        if (StringUtils.isBlank(token)) {
            genericAuthResponse.setCode(ResultCode.PARAM_MISS);
            genericAuthResponse.setMessage("Token is required");

            return genericAuthResponse;
        }

        try {
            DecodedJWT decodedJWT = Sign.verifySessionToken(token, appProps.getSigningSecret());
            String username = decodedJWT.getClaim(Sign.CLAIM_USERNAME).asString();
            String role = decodedJWT.getClaim(Sign.CLAIM_USER_ROLE).asString();
            TokenUserDto tokenUserDto = TokenUserDto.builder()
                    .username(username)
                    .role(role)
                    .build();
            return new GenericAuthResponse(tokenUserDto);
        } catch (Exception e) {
            log.error("fail to verify token", "token", token, e);

            genericAuthResponse.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            genericAuthResponse.setMessage(e.getMessage());
            return genericAuthResponse;
        }
    }
}
