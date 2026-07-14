package com.sifukucoding.liftlink.email.controller;

import com.sifukucoding.liftlink.auth.service.AuthService;
import com.sifukucoding.liftlink.dto.EmailRequest;
import com.sifukucoding.liftlink.dto.VerificationResponse;
import com.sifukucoding.liftlink.email.model.EmailTemplateName;
import com.sifukucoding.liftlink.email.model.EmailVerification;
import com.sifukucoding.liftlink.email.repository.EmailVerificationRepository;
import com.sifukucoding.liftlink.email.service.IEmailService;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test/")
public class EmailController {

    private final IEmailService emailService;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final EmailVerificationRepository verificationRepository;

    @PostMapping("/email")
    public String sendEmail(@RequestBody EmailRequest request,
                            @RequestBody VerificationResponse verificationResponse){
        EmailVerification emailVerification = verificationRepository.findById(verificationResponse.getId())
                .orElseThrow(()->new RuntimeException("No verification code found for this user"));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User with email: " + request.getEmail() + "not found"));

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.fullNames(),
                "Congratulations! Your Mailtrap integration is working.",
                EmailTemplateName.ACTIVATE_ACCOUNT,
                "http://localhost:4200/activate_account",
                emailVerification.getVerificationCode()
        );

        return "Email sent successfully";
    }
}
