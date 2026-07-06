package com.sifukucoding.liftlink.dto;

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

    @NotBlank(message = "Make is required")
    private String make;

    @Column(nullable = false)
    private String model;

    @Min(value = 2008, message = "Car year model must be 2008 or newer")
    private Integer year;

    @NotBlank(message = "Color of the car is required")
    private String color;

    @Min(value = 2)
    @Max(value = 7)
    private Integer seats;

    @NotBlank(message = "License disk expiry is required")
    private String licenseDiskExpiry;

}
