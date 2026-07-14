package com.sifukucoding.liftlink.email.service;

import com.sifukucoding.liftlink.email.model.EmailVerification;
import com.sifukucoding.liftlink.email.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationCleanUpService{
    private final EmailVerificationRepository verificationRepository;

//    @Scheduled(fixedRate = 20000L)
//    public void deleteExpiredCodes(){
//        List<EmailVerification> expiredCodes = verificationRepository
//                .findByExpiredCodesAtBefore(LocalDateTime.now());
//        for(EmailVerification verificationCodes: expiredCodes){
//            if(verificationCodes.getExpiryTime().isAfter(LocalDateTime.now().plusMinutes(15))){
//                verificationRepository.deleteAll();
//            }
//        }
//    }
}
