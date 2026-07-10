package com.sifukucoding.liftlink.email.service;

import com.sifukucoding.liftlink.email.model.EmailTemplateName;

public interface IEmailService {

    void sendVerificationEmail(
            String to,
            String fullName,
            String subject,
            EmailTemplateName emailTemplateName,
            String confirmationUrl,
            String verificationCode
    );

}
