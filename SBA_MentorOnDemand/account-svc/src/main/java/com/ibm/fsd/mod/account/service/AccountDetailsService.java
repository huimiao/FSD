package com.ibm.fsd.mod.account.service;


import com.ibm.fsd.mod.account.dto.UserDto;
import com.ibm.fsd.mod.account.model.Role;
import com.ibm.fsd.mod.account.model.User;
import com.ibm.fsd.mod.account.repository.RoleRepository;
import com.ibm.fsd.mod.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("accountDetailService")
@RequiredArgsConstructor
public class AccountDetailsService {
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    /**
     * refer https://stackoverflow.com/questions/11746499/how-to-solve-the-failed-to-lazily-initialize-a-collection-of-role-hibernate-ex
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<Role> roles = user.getRoles();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto saveUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(10000L).get();

        User userModel = this.convertToModel(user);
        userModel.setRoles(new ArrayList<>());
        userModel.getRoles().add(role);
        return this.convertToDto(userRepository.save(userModel));
    }

    public UserDto getUserDetail(String username) {
        return this.convertToDto(userRepository.findUserByUsername(username));
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToModel(UserDto userDto) {
        return modelMapper.map(userDto
                , User.class);
    }
}
