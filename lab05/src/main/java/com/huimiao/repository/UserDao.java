package com.huimiao.repository;

import com.huimiao.model.User;

public interface UserDao {
    Number saveUser(User user);
    User getUserDetail(String username);
    Number updateUser(User user);
}
