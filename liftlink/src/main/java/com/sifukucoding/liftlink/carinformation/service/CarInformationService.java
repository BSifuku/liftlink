package com.sifukucoding.liftlink.carinformation.service;

import com.sifukucoding.liftlink.carinformation.repository.CarInformationRepository;
import com.sifukucoding.liftlink.dto.CarInformationRequest;
import com.sifukucoding.liftlink.dto.CarInformationResponse;
import com.sifukucoding.liftlink.handler.CarInformationNotFoundException;
import com.sifukucoding.liftlink.handler.InvalidRoleException;
import com.sifukucoding.liftlink.handler.VehicleAlreadyRegisteredException;
import com.sifukucoding.liftlink.model.CarInformation;
import com.sifukucoding.liftlink.model.Role;
import com.sifukucoding.liftlink.model.User;
import com.sifukucoding.liftlink.repository.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CarInformationService implements ICarInformationService {

    private final CarInformationRepository carInformationRepository;
    private final UserRepository userRepository;


    @Override
    public CarInformationResponse registerCar(CarInformationRequest requestCar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        assert authentication != null;
        String email =  authentication.getName();

        User driver = userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User not found"));

        if(driver.getRole() != Role.DRIVER){
            throw new InvalidRoleException("Only drivers can register vehicles.");
        }

        if(carInformationRepository.existsByDriverId(driver.getId())){
            throw new VehicleAlreadyRegisteredException("Driver already has a registered vehicle.");
        }

        if(carInformationRepository.existsByRegistrationNumber(requestCar.getRegistrationNumber())){
            throw new VehicleAlreadyRegisteredException("Registration number already exists.");
        }

        CarInformation carInformation = CarInformation.builder()
                .registrationNumber(requestCar.getRegistrationNumber())
                .make(requestCar.getMake())
                .model(requestCar.getModel())
                .year(requestCar.getYear())
                .color(requestCar.getColor())
                .seats(requestCar.getSeats())
                .licenseDiskExpiry(requestCar.getLicenseDiskExpiry())
                .driver(driver)
                .build();

        CarInformation savedCar = carInformationRepository.save(carInformation);
        return CarInformationResponse.builder()
                .id(savedCar.getId())
                .registrationNumber(savedCar.getRegistrationNumber())
                .make(savedCar.getMake())
                .model(savedCar.getModel())
                .year(savedCar.getYear())
                .color(savedCar.getColor())
                .seats(savedCar.getSeats())
                .licenseDiskExpiry(savedCar.getLicenseDiskExpiry())
                .driverId(savedCar.getId())
                .build();
    }

    @Override
    public CarInformationResponse getCarById(Long id) {
        return null;
    }

    @Override
    public CarInformationResponse getMyCar() {

//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        assert authentication != null;
//        String email = authentication.getName();

//        User driver = userRepository.findByEmail(email).orElseThrow(()->
//                new UsernameNotFoundException("User not found"));

        User driver = getAuthenticatedUser();

        if(driver.getRole() != Role.DRIVER){
            throw new InvalidRoleException("Only driver have registered vehicles.");
        }

        CarInformation carInformation = carInformationRepository.findByDriverId(driver.getId()).orElseThrow(()->
                new CarInformationNotFoundException("No vehicle found for this vehicle."));


        return CarInformationResponse.builder()
                .id(carInformation.getId())
                .registrationNumber(carInformation.getRegistrationNumber())
                .make(carInformation.getMake())
                .model(carInformation.getModel())
                .year(carInformation.getYear())
                .color(carInformation.getColor())
                .seats(carInformation.getSeats())
                .licenseDiskExpiry(carInformation.getLicenseDiskExpiry())
                .driverId(driver.getId())
                .build();
    }

    @Override
    public CarInformationResponse updateCar(Long id, CarInformationRequest requestCar) {
        return null;
    }

    @Override
    public Void deleteCar(Long id) {
        return null;
    }

    private User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
