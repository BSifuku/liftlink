package com.sifukucoding.liftlink.carinformation.controller;

import com.sifukucoding.liftlink.carinformation.service.CarInformationService;
import com.sifukucoding.liftlink.dto.CarInformationRequest;
import com.sifukucoding.liftlink.dto.CarInformationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cars")
public class CarInformationController {

    private final CarInformationService carInformationService;

    @GetMapping("/me")
    public ResponseEntity<CarInformationResponse> getMyCar(){
        return ResponseEntity.ok(carInformationService.getMyCar());
    }

    @PostMapping
    public ResponseEntity<CarInformationResponse> registerCar(@Valid @RequestBody CarInformationRequest request){
        CarInformationResponse response =
                carInformationService.registerCar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
