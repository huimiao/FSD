package com.huimiao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.huimiao.model.User;
import com.huimiao.repository.UserDao;
import com.huimiao.repository.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService implements UserDetailsService {
    private String salt = "123456ef";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    public String generateJwtToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());
        Date date = new Date(System.currentTimeMillis() + 3600 * 1000);

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Number uid = userDao.saveUser(user);
        userRoleDao.addRoleToUser(uid);
    }

    public User getUserDetail(String username) {
        return userDao.getUserDetail(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public Number updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.updateUser(user);
    }
}
