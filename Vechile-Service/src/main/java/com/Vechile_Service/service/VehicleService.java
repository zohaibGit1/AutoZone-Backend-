package com.Vechile_Service.service;

import com.Vechile_Service.dto.VehicleRequestDto;
import com.Vechile_Service.dto.VehicleResponseDto;

public interface VehicleService {
    VehicleResponseDto registerVehicle(VehicleRequestDto vehicleRequestDto);
}