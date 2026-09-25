package com.Vechile_Service.service.impl;

import com.Vechile_Service.dto.VehicleVisitRequestDto;
import com.Vechile_Service.dto.VehicleVisitResponse;
import com.Vechile_Service.entity.Vehicle;
import com.Vechile_Service.entity.VehicleVisit;
import com.Vechile_Service.exception.ResourceNotFoundException;
import com.Vechile_Service.mapper.Mapper;
import com.Vechile_Service.repo.VehicleRepository;
import com.Vechile_Service.repo.VehicleVisitRepository;
import com.Vechile_Service.service.VehicleVisitService;
import org.springframework.stereotype.Service;

@Service
public class VehicleVisitServiceImpl implements VehicleVisitService {

    private final Mapper mapper;
    private final VehicleVisitRepository vehicleVisitRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleVisitServiceImpl(Mapper mapper, VehicleVisitRepository vehicleVisitRepository, VehicleRepository vehicleRepository) {
        this.mapper = mapper;
        this.vehicleVisitRepository = vehicleVisitRepository;
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    public VehicleVisitResponse registerVehicleVisit(VehicleVisitRequestDto vehicleVisit) {

        Vehicle vehicle = vehicleRepository.findById(vehicleVisit.getVehicleId()).orElseThrow(()->
                new ResourceNotFoundException("Vehicle not found with the id " + vehicleVisit.getVehicleId()));
        VehicleVisit vehicleVisit1 = new VehicleVisit();
        vehicleVisit1.setCurrentKm(vehicleVisit.getCurrentKm());
        vehicleVisit1.setVehicle(vehicle);
        vehicleVisitRepository.save(vehicleVisit1);
        return mapper.toResponseDto(vehicleVisit1);
    }
}
