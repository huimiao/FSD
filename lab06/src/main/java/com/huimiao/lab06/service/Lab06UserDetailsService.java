package com.huimiao.lab06.service;

import com.huimiao.lab06.model.Role;
import com.huimiao.lab06.model.User;
import com.huimiao.lab06.repository.RoleRepository;
import com.huimiao.lab06.repository.UserRepository;
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

@Service("userDetailService")
public class Lab06UserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * refer https://stackoverflow.com/questions/11746499/how-to-solve-the-failed-to-lazily-initialize-a-collection-of-role-hibernate-ex
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
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
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(10001).get();
        user.setRoles(new ArrayList<>());
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public User getUserDetail(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(userRepository.update(user));
    }

}
