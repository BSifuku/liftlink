package com.sifukucoding.liftlink.TDOs.user;

import com.sifukucoding.liftlink.model.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;


    private Role role;

    private LocalDate dateOfBirth;

    private String gender;

    private LocalDate cratedAt;

    private boolean active;
}
