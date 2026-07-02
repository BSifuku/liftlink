package com.sifukucoding.liftlink.service;

import com.sifukucoding.liftlink.TDOs.user.UserRequest;
import com.sifukucoding.liftlink.TDOs.user.UserResponse;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import com.sifukucoding.liftlink.serviceinterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existByEmail(request.getEmail())){
            throw new RuntimeException("Email already exist!");
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
                .createdAt(request.getDateOfBirth())
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

