package com.ibm.fsd.mod.account.service;

import com.ibm.fsd.mod.account.dto.UserDto;
import com.ibm.fsd.mod.account.model.Role;
import com.ibm.fsd.mod.account.model.User;
import com.ibm.fsd.mod.account.repository.RoleRepository;
import com.ibm.fsd.mod.account.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class AccountDetailsServiceTest {
    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    AccountDetailsService accountDetailsService;

    @Before
    public void setUp() {
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Test
    public void testGetUserDetail() {
        when(userRepository.findUserByUsername(anyString()))
                .thenReturn(User.builder().username("huimiao@cn.ibm.com").build());
        UserDto userDto = accountDetailsService.getUserDetail("huimiao@cn.ibm.com");
        assertThat(userDto.getUsername()).isEqualTo("huimiao@cn.ibm.com");
        verify(userRepository).findUserByUsername("huimiao@cn.ibm.com");
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(any()))
                .thenReturn(User.builder().username("huimiao@cn.ibm.com").build());
        when(roleRepository.findRoleByRole("ROLE_USER")).thenReturn(Optional.of(Role.builder().role("ROLE_USER").build()));
        UserDto userDto = accountDetailsService.saveUser(UserDto.builder().username("huimiao@cn.ibm.com").isMentor("N").build());
        assertThat(userDto.getUsername()).isEqualTo("huimiao@cn.ibm.com");
    }
}
