package com.ibm.fsd.mod.account.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "{email.format.error}")
    @NotBlank
    @Column(name = "user_name")
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Column(name = "first_name")
    private String firstName = "";
    @NotBlank
    @Column(name = "last_name")
    private String lastName = "";
    @NotBlank
    @Column(name = "contact_number")
    private String contactNumber = "123456789";
    @Column(name = "reg_datetime")
    private Date regDateTime;
    @Column(name = "reg_code")
    private String regCode = "";
    private Boolean active = true;
    @Column(name = "years_of_experience")
    private int yearsOfExperience = 0;
    @Column(name = "linkedin_url")
    private String linkedinUrl = "";
    @Column(name = "confirmed_signup")
    private Boolean confirmedSignup = true;
    @Column(name = "force_reset_password")
    private Boolean forceRestPassword = false;
    @Column(name = "rest_password_datetime")
    private Date restPasswordDateTime;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")}
    )
    List<Role> roles;
}