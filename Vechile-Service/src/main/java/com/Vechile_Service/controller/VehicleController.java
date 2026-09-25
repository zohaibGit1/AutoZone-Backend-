package com.Vechile_Service.controller;

import com.Vechile_Service.dto.VehicleRequestDto;
import com.Vechile_Service.dto.VehicleResponseDto;
import com.Vechile_Service.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/register-vehicle")
    public ResponseEntity<VehicleResponseDto> registerVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleService.registerVehicle(vehicleRequestDto));
    }

}
