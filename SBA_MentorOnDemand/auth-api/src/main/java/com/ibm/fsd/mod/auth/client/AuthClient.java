package com.ibm.fsd.mod.auth.client;

import com.ibm.fsd.mod.auth.AuthConstant;
import com.ibm.fsd.mod.auth.dto.GenericAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = AuthConstant.SERVICE_NAME, path = "/api/v1/auth")
public interface AuthClient {
    @PostMapping("/jwt/{token}")
    GenericAuthResponse verifyJwtToken(@PathVariable String token);
}
