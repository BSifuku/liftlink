package com.sifukucoding.liftlink.controller;


import com.sifukucoding.liftlink.TDOs.user.UserRequest;
import com.sifukucoding.liftlink.TDOs.user.UserResponse;
import com.sifukucoding.liftlink.serviceinterface.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRequest request){

        return ResponseEntity.ok(userService.register(request));
    }
}
