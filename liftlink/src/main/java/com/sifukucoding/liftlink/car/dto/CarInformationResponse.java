package com.sifukucoding.liftlink.car.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarInformationResponse {

    private Long id;

    private String registrationNumber;

    private String make;

    private String model;

    private Integer year;

    private String color;

    private Integer seats;

    private boolean luggageSpace;

    private boolean airConditioning;

    private boolean active;

    private String licenceDiskExpiry;

    private Long driverId;

    private String driverFullNames;

    private String driverEmail;


}
