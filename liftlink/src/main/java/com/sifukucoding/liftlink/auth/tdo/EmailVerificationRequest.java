package com.sifukucoding.liftlink.auth.tdo;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private  String email;

    @NotBlank(message = "Verification code is required")
    private String verificationCode;
}
