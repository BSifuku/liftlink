package com.sifukucoding.liftlink.car.repository;

import com.sifukucoding.liftlink.car.model.CarInformation;
import com.sifukucoding.liftlink.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarInformationRepository extends JpaRepository<CarInformation, Long> {

    Optional<CarInformation> findByDriverId(Long driverId);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByDriverId(Long driverId);

    Optional<CarInformation> findByDriver(User driverId);
}
