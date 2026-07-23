package com.sifukucoding.liftlink.car.controller;

import com.sifukucoding.liftlink.car.service.CarInformationService;
import com.sifukucoding.liftlink.car.dto.CarInformationRequest;
import com.sifukucoding.liftlink.car.dto.CarInformationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cars")
public class CarInformationController {

    private final CarInformationService carInformationService;

    @GetMapping("/me")
    public ResponseEntity<CarInformationResponse> getMyCar(){
        return ResponseEntity.ok(carInformationService.getMyCar());
    }

    @PostMapping("/register")
    public ResponseEntity<CarInformationResponse> registerCar(@Valid @RequestBody CarInformationRequest request){
        CarInformationResponse response =
                carInformationService.registerCar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/me")
//    public ResponseEntity<CarInformationResponse> getCarById(
//            Authentication connectedUser) {
//
//        return ResponseEntity.ok(carInformationService.getCarById(connectedUser));
//    }

    @PutMapping("/me")
    public ResponseEntity<CarInformationResponse> updateCar(
            Authentication connectedUser,
            @Valid @RequestBody CarInformationRequest request) {

        return ResponseEntity.ok(carInformationService.updateCar(connectedUser, request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCar(Authentication connectedUser) {

        carInformationService.deleteCar(connectedUser);

        return ResponseEntity.noContent().build();
    }

}
