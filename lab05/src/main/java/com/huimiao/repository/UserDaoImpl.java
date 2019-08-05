package com.huimiao.repository;

import com.huimiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    JdbcOperations jdbcTemplate;

    SimpleJdbcInsertOperations simpleJdbcInsert;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
        this.simpleJdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    public Number saveUser(User user) {
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("first_name", user.getFirst_name());
        parameters.put("last_name", user.getLast_name());
        parameters.put("email", user.getEmail());
        parameters.put("enabled", "true");
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return key;
    }

    @Override
    public User getUserDetail(String username) {
        return jdbcTemplate.query("select username, first_name, last_name, email from users where username = ?",
                new String[]{username},
                rs -> {
                    rs.next();
                    User user = new User();
                    user.setUsername(rs.getString(1));
                    user.setFirst_name(rs.getString(2));
                    user.setLast_name(rs.getString(3));
                    user.setEmail(rs.getString(4));

                    return user;
                });
    }

    @Override
    public Number updateUser(User user) {
        return jdbcTemplate.update("update users set first_name =?, last_name=?, email=? where username=?",
                new String[]{user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getUsername()});
    }
}
