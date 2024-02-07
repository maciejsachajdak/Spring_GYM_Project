package com.gymapplication.club_service.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1922476556453977075L;

    private Long id;

    private String name;

    private String surname;

    private String address;

    private String sex;

    private Date birthDate;

    private String login;

    private String email;

    private String password;

    private Integer clubNumber;

    private Integer passNumber;

    private String roleName;

}
