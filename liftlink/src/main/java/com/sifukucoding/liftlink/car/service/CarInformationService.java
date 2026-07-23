package com.sifukucoding.liftlink.car.service;

import com.sifukucoding.liftlink.car.repository.CarInformationRepository;
import com.sifukucoding.liftlink.car.dto.CarInformationRequest;
import com.sifukucoding.liftlink.car.dto.CarInformationResponse;
import com.sifukucoding.liftlink.handler.*;
import com.sifukucoding.liftlink.car.model.CarInformation;
import com.sifukucoding.liftlink.user.model.Role;
import com.sifukucoding.liftlink.user.model.User;
import com.sifukucoding.liftlink.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.sifukucoding.liftlink.car.mapper.CarInformationMapper.mapToResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class CarInformationService implements ICarInformationService {

    private final CarInformationRepository carRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public CarInformationResponse registerCar(CarInformationRequest request) {

        User driver = getAuthenticatedUser();

        if (driver.getRole() != Role.DRIVER) {
            throw new InvalidRoleException("Only drivers can register vehicles.");
        }

        if (carRepository.existsByDriverId(driver.getId())) {
            throw new VehicleAlreadyRegisteredException(
                    "Driver already has a registered vehicle.");
        }

        if (carRepository.existsByRegistrationNumber(
                request.getRegistrationNumber())) {

            throw new VehicleAlreadyRegisteredException(
                    "Registration number already exists.");
        }

        CarInformation car = CarInformation.builder()
                .registrationNumber(request.getRegistrationNumber())
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .color(request.getColor())
                .seats(request.getSeats())
                .licenceDiskExpiry(request.getLicenceDiskExpiry())
                .airConditioning(request.isAirConditioning())
                .luggageSpace(request.getLuggageSpace())
                .active(true)
                .driver(driver)
                .build();

        return mapToResponse(carRepository.save(car));
    }

    @Override
    public CarInformationResponse getCarById(Authentication connectedUser) {
        User driver = getAuthenticatedUser();

        if(driver.getRole() != Role.DRIVER){
            throw new InvalidRoleException("Only drivers can register vehicles.");
        }

        CarInformation car = carRepository.findById(driver.getId())
                .orElseThrow(() ->
                        new CarInformationNotFoundException(
                                "Vehicle not found."));

        return mapToResponse(car);
    }

    @Override
    public CarInformationResponse getMyCar() {


        User driver = getAuthenticatedUser();

        if(driver.getRole() != Role.DRIVER){
            throw new InvalidRoleException("Only driver have registered vehicles.");
        }

        CarInformation carInformation = carRepository.findByDriverId(driver.getId()).orElseThrow(()->
                new CarInformationNotFoundException("No vehicle found for this vehicle."));

        return mapToResponse(carInformation);
    }

    @Override
    @Transactional
    public CarInformationResponse updateCar(
            Authentication connectedUser,
            CarInformationRequest request) {

        User driver = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        CarInformation car = carRepository.findByDriver(driver)
                .orElseThrow(() ->
                        new CarInformationNotFoundException(
                                "Vehicle not found."));

        car.setRegistrationNumber(request.getRegistrationNumber());
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setColor(request.getColor());
        car.setSeats(request.getSeats());
        car.setLicenceDiskExpiry(request.getLicenceDiskExpiry());
        car.setAirConditioning(request.isAirConditioning());
        car.setLuggageSpace(request.getLuggageSpace());

        return mapToResponse(carRepository.save(car));
    }

    @Override
    @Transactional
    public void deleteCar(Authentication connectedUser) {

        User driver = (User) connectedUser.getPrincipal();

        CarInformation car = carRepository.findByDriver(driver)
                .orElseThrow(() ->
                        new CarInformationNotFoundException(
                                "Vehicle information not found."));

        carRepository.delete(car);
    }

    private User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
