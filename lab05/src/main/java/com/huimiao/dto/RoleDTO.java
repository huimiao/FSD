package com.huimiao.dto;

import com.huimiao.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

@Getter
@Setter
@ToString
public class RoleDTO extends Role {
    private Set<User> users;
}
