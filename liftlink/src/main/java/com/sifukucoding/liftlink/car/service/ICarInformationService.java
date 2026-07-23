package com.sifukucoding.liftlink.car.service;

import com.sifukucoding.liftlink.car.dto.CarInformationRequest;
import com.sifukucoding.liftlink.car.dto.CarInformationResponse;
import org.springframework.security.core.Authentication;

public interface ICarInformationService {

    CarInformationResponse registerCar(CarInformationRequest requestCar);
    CarInformationResponse getCarById(Authentication connectedUser);
    CarInformationResponse getMyCar();
    CarInformationResponse updateCar(Authentication connectedUser,
                                     CarInformationRequest requestCar);
    void deleteCar(Authentication connectedUser);
}
