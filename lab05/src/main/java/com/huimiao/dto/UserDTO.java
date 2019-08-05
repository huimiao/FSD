package com.huimiao.dto;

import com.huimiao.model.Role;
import com.huimiao.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName: UserDTO
 * @Description: TODO
 * @author: huimiao
 * @date: 7/31/2019 12:51 AM
 * @version: 1.0
 */
@Getter
@Setter
@ToString
public class UserDTO extends User {
    List<Role> roles;
}
