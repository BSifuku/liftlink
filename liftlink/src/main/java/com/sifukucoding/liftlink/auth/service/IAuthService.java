package com.sifukucoding.liftlink.auth.service;

import com.sifukucoding.liftlink.auth.tdo.AuthenticationResponse;
import com.sifukucoding.liftlink.auth.tdo.LoginRequest;
import com.sifukucoding.liftlink.auth.tdo.UserRequest;
import com.sifukucoding.liftlink.auth.tdo.UserResponse;

import java.util.List;

public interface IAuthService {
    UserResponse register(UserRequest request);
    AuthenticationResponse login(LoginRequest request);

}
