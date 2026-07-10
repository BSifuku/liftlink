package com.sifukucoding.liftlink.email.controller;

import com.sifukucoding.liftlink.auth.service.AuthService;
import com.sifukucoding.liftlink.email.model.EmailTemplateName;
import com.sifukucoding.liftlink.email.service.IEmailService;
import com.sifukucoding.liftlink.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/")
public class EmailController {

    private final IEmailService emailService;
    private final AuthService authService;

    @GetMapping("/email")
    public String sendEmail(){
        User user = new User();

        emailService.sendVerificationEmail(

                "test@example.com",
                "Sifuku Bulelani",
                "Congratulations! Your Mailtrap integration is working.",
                EmailTemplateName.ACTIVATE_ACCOUNT,
                "http://localhost:4200/activate_account",
                authService.generateAndSaveActivationToken(user)
        );

        return "Email sent successfully";
    }
}
