package com.sifukucoding.liftlink.carinformation.repository;

import com.sifukucoding.liftlink.model.CarInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarInformationRepository extends JpaRepository<CarInformation, Long> {

    Optional<CarInformation> findByDriverId(Long driverId);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByDriverId(Long driverId);

}
