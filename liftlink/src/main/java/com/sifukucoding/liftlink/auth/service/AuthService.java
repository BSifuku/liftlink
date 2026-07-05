package com.sifukucoding.liftlink.auth.service;

import com.sifukucoding.liftlink.auth.tdo.AuthenticationResponse;
import com.sifukucoding.liftlink.auth.tdo.LoginRequest;
import com.sifukucoding.liftlink.auth.tdo.UserRequest;
import com.sifukucoding.liftlink.auth.tdo.UserResponse;
import com.sifukucoding.liftlink.handler.UserAlreadyExistsException;
import com.sifukucoding.liftlink.handler.InvalidRoleException;
import com.sifukucoding.liftlink.handler.UserNotFoundException;
import com.sifukucoding.liftlink.model.Role;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import com.sifukucoding.liftlink.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;
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
                .password(request.getPassword())
                .role(request.getRole())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .createdAt(LocalDate.now())
                .active(false)
                .build();

        User saved = userRepository.save(user);
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

