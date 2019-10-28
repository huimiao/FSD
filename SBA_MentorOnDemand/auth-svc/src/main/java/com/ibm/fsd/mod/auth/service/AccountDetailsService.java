package com.ibm.fsd.mod.auth.service;

import com.ibm.fsd.mod.account.client.AccountClient;
import com.ibm.fsd.mod.account.dto.GenericAccountResponse;
import com.ibm.fsd.mod.account.dto.RoleDto;
import com.ibm.fsd.mod.account.dto.UserDto;
import com.ibm.fsd.mod.common.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Slf4j
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountClient accountClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GenericAccountResponse resp = null;
        try {
            resp = accountClient.getAccountDetailsByUsername(username);
        } catch (Exception ex) {
            String errMsg = "fail to get user";
            log.error(errMsg, ex);
            throw new ServiceException(errMsg, ex);
        }
        if (!resp.isSuccess()) {
            log.error(resp.getMessage());
            throw new ServiceException(resp.getMessage());
        }

        UserDto userDto = resp.getAccount();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (userDto == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<RoleDto> roles = userDto.getRoles();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole())));

        return new org.springframework.security.core.userdetails.User(userDto.getUsername(),
                userDto.getPassword(),
                grantedAuthorities);
    }
}
