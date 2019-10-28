package com.ibm.fsd.mod.account.repository;

import com.ibm.fsd.mod.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "update users set first_name =:#{#user.first_name}, last_name=:#{#user.last_name}, email=:#{#user.email}, password = :#{#user.password} where username=:#{#user.username}")
    int update(@Param("user") User user);
}
