package com.sifukucoding.liftlink.repository;

import com.sifukucoding.liftlink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existByEmail(String email);
    boolean existByPhoneNumber(String phoneNumber);
}
