package com.sifukucoding.liftlink.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_information")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private String licenseDiskExpiry;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private User driver;
}
