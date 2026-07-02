package com.sifukucoding.liftlink.handler;

public abstract class BaseException extends RuntimeException{
    protected BaseException(String message) {
        super(message);
    }
}

