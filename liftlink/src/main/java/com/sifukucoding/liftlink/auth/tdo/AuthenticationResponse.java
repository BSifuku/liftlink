package com.sifukucoding.liftlink.auth.tdo;

import com.sifukucoding.liftlink.user.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private  String token;
    private  String tokenType;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
