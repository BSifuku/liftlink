package com.sifukucoding.liftlink.dto;

import com.sifukucoding.liftlink.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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

    private String licenseDiskExpiry;

    private Long driverId;


}
