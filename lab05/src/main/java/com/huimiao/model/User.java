package com.huimiao.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class User {
    private int id;
    @NotBlank
    private String username;
    @NotBlank
    @Min(6)
    private String password;
    @NotBlank
    private String first_name;
    @NotBlank
    private String last_name;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+", message = "Please input a valid Email.")
    @NotBlank
    private String email;
}
