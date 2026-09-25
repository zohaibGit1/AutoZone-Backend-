package com.Vechile_Service.controller;

import com.Vechile_Service.dto.VehicleVisitRequestDto;
import com.Vechile_Service.dto.VehicleVisitResponse;
import com.Vechile_Service.service.VehicleVisitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/api/v1/vehicle-visit")
public class VehicleVisitController {


    private final VehicleVisitService vehicleVisitService;

    public VehicleVisitController(VehicleVisitService vehicleVisitService) {
        this.vehicleVisitService = vehicleVisitService;
    }

    @PostMapping
    public ResponseEntity<VehicleVisitResponse> registerVehicleVisit(
            @Valid @RequestBody VehicleVisitRequestDto vehicleVisitRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleVisitService.registerVehicleVisit(vehicleVisitRequest));
    }
}
