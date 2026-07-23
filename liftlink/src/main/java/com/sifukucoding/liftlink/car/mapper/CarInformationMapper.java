package com.sifukucoding.liftlink.car.mapper;

import com.sifukucoding.liftlink.car.dto.CarInformationResponse;
import com.sifukucoding.liftlink.car.model.CarInformation;

public class CarInformationMapper {
    public static CarInformationResponse mapToResponse(CarInformation car) {

        return  CarInformationResponse.builder()
                .id(car.getId())
                .registrationNumber(car.getRegistrationNumber())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .color(car.getColor())
                .seats(car.getSeats())
                .licenceDiskExpiry(car.getLicenceDiskExpiry())
                .airConditioning(car.isAirConditioning())
                .luggageSpace(car.isLuggageSpace())
                .active(car.isActive())
                .driverId(car.getDriver().getId())
                .driverFullNames(car.getDriver().fullNames())
                .driverEmail(car.getDriver().getEmail())
                .build();
    }


}
