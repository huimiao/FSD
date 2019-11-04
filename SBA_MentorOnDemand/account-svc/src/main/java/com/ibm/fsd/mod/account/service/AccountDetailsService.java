package com.ibm.fsd.mod.account.service;


import com.ibm.fsd.mod.account.dto.UserDto;
import com.ibm.fsd.mod.account.model.Role;
import com.ibm.fsd.mod.account.model.User;
import com.ibm.fsd.mod.account.repository.RoleRepository;
import com.ibm.fsd.mod.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("accountDetailService")
@RequiredArgsConstructor
public class AccountDetailsService {
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserDto saveUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String isMentor = user.getIsMentor();
        Role role = roleRepository.findRoleByRole("Y".equals(isMentor) ? "ROLE_MENTOR" : "ROLE_USER").get();
        User userModel = this.convertToModel(user);
        userModel.setRoles(new ArrayList<>());
        userModel.getRoles().add(role);
        return this.convertToDto(userRepository.save(userModel));
    }

    public UserDto getUserDetail(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return this.convertToDto(user);
        } else {
            return null;
        }
    }

    public UserDto getUserProfileWithoutSensitiveInfo(String username) {
        UserDto userDto = getUserDetail(username);
        userDto.setPassword("xxxx");

        return userDto;
    }


    private UserDto convertToDto(User user) {
        if (user == null)
            return null;
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToModel(UserDto userDto) {
        if (userDto == null)
            return null;
        return modelMapper.map(userDto
                , User.class);
    }
}
