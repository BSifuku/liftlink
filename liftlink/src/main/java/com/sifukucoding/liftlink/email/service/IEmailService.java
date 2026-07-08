package com.sifukucoding.liftlink.email.service;

public interface IEmailService {

    void sendVerificationEmail(
            String email,
            String firstName,
            String code
    );

}
