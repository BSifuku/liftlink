package com.sifukucoding.liftlink.carinformation.service;

import com.sifukucoding.liftlink.dto.CarInformationRequest;
import com.sifukucoding.liftlink.dto.CarInformationResponse;

public interface ICarInformationService {

    CarInformationResponse registerCar(CarInformationRequest requestCar);
    CarInformationResponse getCarById(Long id);
    CarInformationResponse getMyCar();
    CarInformationResponse updateCar(Long id, CarInformationRequest requestCar);
    Void deleteCar(Long id);
}
