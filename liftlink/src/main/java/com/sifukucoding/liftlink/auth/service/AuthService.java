package com.sifukucoding.liftlink.auth.service;

import com.sifukucoding.liftlink.auth.tdo.*;
import com.sifukucoding.liftlink.email.model.EmailTemplateName;
import com.sifukucoding.liftlink.email.model.EmailVerification;
import com.sifukucoding.liftlink.email.repository.EmailVerificationRepository;
import com.sifukucoding.liftlink.email.service.EmailService;
import com.sifukucoding.liftlink.handler.*;
import com.sifukucoding.liftlink.model.Role;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import com.sifukucoding.liftlink.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailVerificationRepository verificationRepository;
    private final EmailService emailService;

    @Value("${application.frontend.url}")
    private String activationUrl;
    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException(
                    "A user with email " + request.getEmail() + " already exists."
            );
        }

        if(request.getRole() == Role.ADMIN){
            throw new InvalidRoleException("You cannot register as ADMIN");
        }

         User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .createdAt(LocalDate.now())
                .active(false)
                .build();

        User saved = userRepository.save(user);
        sendValidationEmail(user);


        return UserResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .phoneNumber(saved.getPhoneNumber())
                .role(saved.getRole())
                .dateOfBirth(saved.getDateOfBirth())
                .gender(saved.getGender())
                .cratedAt(saved.getCreatedAt())
                .active(saved.isActive())
                .build();

    }

//    private void sendValidationEmail(User user) {
//        var newVerificationCode = generateAndSaveActivationToken(user);
//
//        //send email
//        emailService.sendVerificationEmail(user.getEmail(),
//                user.fullNames(),
//                newVerificationCode,
//                EmailTemplateName.ACTIVATE_ACCOUNT,
//                activationUrl,
//                newVerificationCode);
//    }

    private void sendValidationEmail(User user) {

        String verificationCode = generateOrUpdateActivationToken(user);

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.fullNames(),
                verificationCode,
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                verificationCode
        );
    }

    private String generateOrUpdateActivationToken(User user) {

        String generatedCode = generateActivationCode(6);

        EmailVerification verification = verificationRepository
                .findByUser(user)
                .orElse(
                        EmailVerification.builder()
                                .user(user)
                                .createdAt(LocalDateTime.now())
                                .build()
                );

        verification.setVerificationCode(generatedCode);
        verification.setExpiryTime(LocalDateTime.now().plusMinutes(15));
        verification.setUsed(false);

        verificationRepository.save(verification);

        return generatedCode;
    }

    public String generateActivationCode(int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        if (!user.isActive()) {

            sendValidationEmail(user);

            throw new AccountNotVerifiedException(
                    "Your account is not verified. A new verification code has been sent to your email."
            );
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );




        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()

                .token(token)
                .tokenType("Bearer")
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())

                .build();

    }

    @Override
    @Transactional
    public void verifyEmail(EmailVerificationRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->
                new UsernameNotFoundException("User not found"));

        EmailVerification verification = verificationRepository.findByUser(user).orElseThrow(()->
                new VerificationCodeNotFoundException("Verification code not found"));

        if(verification.isUsed()){
            throw new VerificationCodeAlreadyUsedException(
                    "Verification code has already been used.");
        }

        if (!verification.getVerificationCode()
                .equals(request.getVerificationCode())) {

            throw new InvalidVerificationCodeException(
                    "Invalid verification code.");
        }

        if (verification.getExpiryTime().isBefore(LocalDateTime.now())) {
            sendValidationEmail(user);
            throw new VerificationCodeExpiredException(
                    "Verification code has expired, we have send a new verification code to your email.");

        }

        user.setActive(true);
        verification.setUsed(true);

        userRepository.save(user);
        verificationRepository.save(verification);

    }


}

