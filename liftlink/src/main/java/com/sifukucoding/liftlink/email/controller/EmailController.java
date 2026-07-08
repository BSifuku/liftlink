package com.sifukucoding.liftlink.email.controller;

import com.sifukucoding.liftlink.email.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/")
public class EmailController {

    private final IEmailService emailService;

    @GetMapping("/email")
    public String sendEmail(){
        emailService.sendVerificationEmail(
                "test@example.com",
                "LIFTLINK TEST",
                "Congratulations! Your Mailtrap integration is working."
        );

        return "Email sent successfully";
    }
}
