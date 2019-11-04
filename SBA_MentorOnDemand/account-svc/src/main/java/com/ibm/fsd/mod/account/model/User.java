package com.ibm.fsd.mod.account.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_name"}))
@DynamicInsert
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "{email.format.error}")
    @NotBlank
    @Column(name = "user_name", length = 128)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Column(name = "first_name")
    private String firstName = "";
    @NotBlank
    @Column(name = "last_name")
    private String lastName = "";
    @Positive
    @Column(name = "contact_number")
    private Long contactNumber = 123456789L;
    @Column(name = "reg_datetime")
    private Timestamp regDateTime;
    private Boolean active = true;
    @Column(name = "confirmed_signup")
    private Boolean confirmedSignup = true;
    @Column(name = "force_reset_password")
    private Boolean forceRestPassword = false;
    @Column(name = "rest_password_datetime")
    private Timestamp restPasswordDateTime;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "id")}
    )
    List<Role> roles;
}
