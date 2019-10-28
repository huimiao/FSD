package com.ibm.fsd.mod.account.client;

import com.ibm.fsd.mod.account.AccountConstant;
import com.ibm.fsd.mod.account.dto.GenericAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = AccountConstant.SERVICE_NAME, path = "/api/v1/account")
public interface AccountClient {
    @PostMapping(path = "/{username}")
    GenericAccountResponse getAccountDetailsByUsername(@PathVariable String username);
}
