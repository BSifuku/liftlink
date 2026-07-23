package com.sifukucoding.liftlink.auth.tdo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;

    private String verificationCode;

    private String newPassword;

    private String confirmPassword;
}
