package com.sifukucoding.liftlink.auth.service;

import com.sifukucoding.liftlink.auth.tdo.AuthenticationResponse;
import com.sifukucoding.liftlink.auth.tdo.LoginRequest;
import com.sifukucoding.liftlink.auth.tdo.UserRequest;
import com.sifukucoding.liftlink.auth.tdo.UserResponse;
import com.sifukucoding.liftlink.email.model.EmailTemplateName;
import com.sifukucoding.liftlink.email.model.EmailVerification;
import com.sifukucoding.liftlink.email.repository.EmailVerificationRepository;
import com.sifukucoding.liftlink.email.service.EmailService;
import com.sifukucoding.liftlink.handler.UserAlreadyExistsException;
import com.sifukucoding.liftlink.handler.InvalidRoleException;
import com.sifukucoding.liftlink.handler.UserNotFoundException;
import com.sifukucoding.liftlink.model.Role;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import com.sifukucoding.liftlink.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
                .active(true)
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

    private void sendValidationEmail(User user) {
        var newVerificationCode = generateAndSaveActivationToken(user);

        //send email
        emailService.sendVerificationEmail(user.getEmail(),
                user.fullNames(),
                newVerificationCode,
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newVerificationCode);
    }

    public String generateAndSaveActivationToken(User user) {
        String generatedCode = generateActivationCode(6);

        var verificationCode = EmailVerification.builder()
                .verificationCode(generatedCode)
                .used(false)
                .expiryTime(LocalDateTime.now().plusMinutes(15))
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        verificationRepository.save(verificationCode);
        return generatedCode;
    }

    private String generateActivationCode(int length) {

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

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

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


}

