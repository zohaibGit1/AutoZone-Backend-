package com.Vechile_Service.service;

import com.Vechile_Service.dto.VehicleVisitRequestDto;
import com.Vechile_Service.dto.VehicleVisitResponse;

public interface VehicleVisitService {
    VehicleVisitResponse registerVehicleVisit(VehicleVisitRequestDto vehicleVisit);

}