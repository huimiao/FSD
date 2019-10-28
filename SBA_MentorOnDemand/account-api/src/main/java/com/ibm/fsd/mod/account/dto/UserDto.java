package com.ibm.fsd.mod.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+", message = "Please input a valid Email.")
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName = "";
    @NotBlank
    private String lastName = "";
    @NotBlank
    private String contactNumber = "123456789";
    private Date regDateTime;
    private String regCode = "";
    private Boolean active = true;
    private int yearsOfExperience = 0;
    private String linkedinUrl = "";
    private Boolean confirmedSignup = true;
    private Boolean forceRestPassword = false;
    private Date restPasswordDateTime;
    List<RoleDto> roles;
}