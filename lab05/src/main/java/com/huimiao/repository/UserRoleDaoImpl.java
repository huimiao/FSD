package com.huimiao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao {
    @Autowired
    JdbcOperations jdbcTemplate;

    @Override
    public Number addRoleToUser(Number uid) {
        final String sql = "insert into user_role (uid,rid)values(?,10001)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, uid.intValue());
            return ps;
        }, holder);

        int rid = holder.getKey().intValue();
        return rid;
    }
}
