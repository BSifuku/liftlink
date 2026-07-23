package com.sifukucoding.liftlink.auth.service;

import com.sifukucoding.liftlink.auth.tdo.*;

import java.util.List;

public interface IAuthService {
    UserResponse register(UserRequest request);
    AuthenticationResponse login(LoginRequest request);
    void verifyEmail(EmailVerificationRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);

}
