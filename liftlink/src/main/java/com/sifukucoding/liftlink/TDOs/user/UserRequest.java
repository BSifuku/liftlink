package com.sifukucoding.liftlink.TDOs.user;

import com.sifukucoding.liftlink.model.Role;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private Role role;

    private LocalDate dateOfBirth;

    private String gender;

    private LocalDate createdAt;

    private boolean active;
}
