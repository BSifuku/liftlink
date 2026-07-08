package com.sifukucoding.liftlink.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendVerificationEmail(String to, String fullName, String verificationCode) {

        try {
            Context context = new Context();
            context.setVariable("name", fullName);
            context.setVariable("verificationCode", verificationCode);
            context.setVariable("expiryTime", 10);

            String htmlContent = templateEngine.process("email-verification", context);

            MimeMessage message =javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Verify your LiftLink account");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (MessagingException | MailException e) {
            throw new RuntimeException(e);
        }

    }
}
