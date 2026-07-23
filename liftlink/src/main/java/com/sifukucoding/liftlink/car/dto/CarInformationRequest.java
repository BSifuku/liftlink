package com.sifukucoding.liftlink.car.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarInformationRequest {

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Vehicle make is required")
    private String make;

    @NotBlank(message = "Vehicle model is required.")
    private String model;

    @NotBlank(message = "Vehicle year is required.")
    @Min(value = 2010, message = "Vehicle year must be 2010 or later.")
    private Integer year;

    @NotBlank(message = "Vehicle color is required")
    private String color;

    @NotBlank(message = "Number of seats is required.")
    @Min(value = 2, message = "Vehicle must have at least 2 seats.")
    @Max(value = 7, message = "Vehicle cannot have more than 7 seats.")
    private Integer seats;

    @NotBlank(message = "Air conditioning is required")
    private boolean airConditioning;

    @NotBlank(message = "License disk expiry is required")
    private String licenceDiskExpiry;

    @NotBlank(message = "Please specify if the vehicle has luggage space.")
    private Boolean luggageSpace;

}
