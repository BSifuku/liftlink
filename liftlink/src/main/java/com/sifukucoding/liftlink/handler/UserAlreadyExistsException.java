package com.sifukucoding.liftlink.handler;

public class UserAlreadyExistsException extends BaseException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
