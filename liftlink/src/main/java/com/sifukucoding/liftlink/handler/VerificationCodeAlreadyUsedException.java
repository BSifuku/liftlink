package com.sifukucoding.liftlink.handler;

public class VerificationCodeAlreadyUsedException extends BaseException {
    public VerificationCodeAlreadyUsedException(String message) {
        super(message);
    }
}
