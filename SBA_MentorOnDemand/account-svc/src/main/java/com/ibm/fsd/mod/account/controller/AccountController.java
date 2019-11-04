package com.ibm.fsd.mod.account.controller;

import com.ibm.fsd.mod.account.dto.GenericAccountResponse;
import com.ibm.fsd.mod.account.dto.UserDto;
import com.ibm.fsd.mod.account.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/v1")
@Validated
public class AccountController {
    @Autowired
    private AccountDetailsService accountDetailsService;

    @GetMapping("/account/{username}")
    public GenericAccountResponse getAccountProfileByUsername(@PathVariable @Email(message = "{email.format.error}") String username) {
        UserDto userDto = accountDetailsService.getUserProfileWithoutSensitiveInfo(username);

        GenericAccountResponse response = new GenericAccountResponse(userDto);

        return response;
    }

    @PostMapping("/account/{username}")
    public GenericAccountResponse getAccountDetailByUsername(@PathVariable @Email(message = "{email.format.error}") String username) {

        UserDto userDto = accountDetailsService.getUserDetail(username);

        GenericAccountResponse response = new GenericAccountResponse(userDto);

        return response;
    }

    @PostMapping(value = "/signup",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public GenericAccountResponse signup(@RequestBody @Validated UserDto user) {

        UserDto userDto = accountDetailsService.saveUser(user);

        GenericAccountResponse response = new GenericAccountResponse(userDto);

        return response;
    }
}
