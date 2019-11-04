package com.ibm.fsd.mod.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Positive
    private Long contactNumber = 123456789L;
    private Timestamp regDateTime;
    private Boolean active = true;
    private Boolean confirmedSignup = true;
    private Boolean forceRestPassword = false;
    private Timestamp restPasswordDateTime;
    private String isMentor = "N";
    List<RoleDto> roles;
}
