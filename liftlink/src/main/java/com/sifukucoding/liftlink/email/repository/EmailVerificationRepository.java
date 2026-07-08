package com.sifukucoding.liftlink.email.repository;

import com.sifukucoding.liftlink.email.model.EmailVerification;
import com.sifukucoding.liftlink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository
        extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByUser(User user);

    Optional<EmailVerification> findByVerificationCode(String verificationCode);
}
