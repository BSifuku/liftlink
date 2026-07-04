package com.sifukucoding.liftlink.service;

import com.sifukucoding.liftlink.TDOs.user.UserRequest;
import com.sifukucoding.liftlink.TDOs.user.UserResponse;
import com.sifukucoding.liftlink.handler.UserAlreadyExistsException;
import com.sifukucoding.liftlink.handler.InvalidRoleException;
import com.sifukucoding.liftlink.model.Role;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import com.sifukucoding.liftlink.serviceinterface.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
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
    public UserResponse getUserById(Long id) {
       return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}

