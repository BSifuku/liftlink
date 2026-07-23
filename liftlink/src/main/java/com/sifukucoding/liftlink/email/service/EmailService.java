package com.sifukucoding.liftlink.email.service;

import com.sifukucoding.liftlink.email.model.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class EmailService implements IEmailService {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendVerificationEmail(
            String to,
            String fullName,
            String subject,
            EmailTemplateName emailTemplateName,
            String confirmationUrl,
            String verificationCode
    ) {
        String templateName;
        if(emailTemplateName == null){
            templateName = "activate_account";
        }else{
            templateName = emailTemplateName.name();
        }

        try {

            MimeMessage mimeMessage =javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name()
                    );

            Map<String, Object> properties = new HashMap<>();
            properties.put("fullName", fullName);
            properties.put("confirmationUrl", confirmationUrl);
            properties.put("verificationCode", verificationCode);

            Context context = new Context();

            context.setVariables(properties);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            String template = templateEngine.process(templateName, context);

            helper.setText(template, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException | MailException e) {
            throw new RuntimeException(e);
        }

    }
}
