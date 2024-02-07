package com.gymapplication.user_panel_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;

    @NotEmpty(message = "Field should not be empty")
    private String name;

    @NotEmpty(message = "Field should not be empty")
    private String surname;

    @NotEmpty(message = "Field should not be empty")
    private String address;

    private Date birthDate;

    @NotEmpty(message = "Field should not be empty")
    private String sex;

    @NotEmpty(message = "Field should not be empty")
    private String login;

    private Integer clubNumber;

    private Integer passNumber;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "This field should not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-+=]).{8,20}$",
            message = "This password must be between 8 and 20 characters long, contain one uppercase letter, one digit, and one special character."
    )
    private String password;

    @NotEmpty(message = "This field should not be empty")
    private String oldPassword;

    @NotEmpty(message = "This field should not be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-+=]).{8,20}$",
            message = "This password must be between 8 and 20 characters long, contain one uppercase letter, one digit, and one special character."
    )
    private String newPassword;

    private String roleName;

    @NotEmpty(message = "This field should not be empty")
    private String confirmNewPassword;
}
