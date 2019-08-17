package com.huimiao.lab06.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Column(name = "first_name")
    private String first_name;
    @NotBlank
    @Column(name = "last_name")
    private String last_name;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+", message = "Please input a valid Email.")
    @NotBlank
    private String email;
    private boolean enabled;

    @ManyToMany()
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name="uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="rid", referencedColumnName = "id")}
    )
    List<Role> roles;
}