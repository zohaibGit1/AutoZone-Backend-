package com.Vechile_Service.service.impl;

import com.Vechile_Service.dto.VehicleRequestDto;
import com.Vechile_Service.dto.VehicleResponseDto;
import com.Vechile_Service.entity.Customer;
import com.Vechile_Service.entity.Vehicle;
import com.Vechile_Service.exception.ResourceNotFoundException;
import com.Vechile_Service.mapper.Mapper;
import com.Vechile_Service.repo.CustomerRepository;
import com.Vechile_Service.repo.VehicleRepository;
import com.Vechile_Service.service.VehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final Mapper mapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, CustomerRepository customerRepository, Mapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public VehicleResponseDto registerVehicle(VehicleRequestDto vehicleRequestDto) {

        Customer customerSearch = customerRepository.findById
                (vehicleRequestDto.getCustomerId()).orElseThrow(()
                -> new ResourceNotFoundException("Customer Not Found with this id " + vehicleRequestDto.getCustomerId()));

        Vehicle vehicle = Vehicle.builder()
                .vehicleModel(vehicleRequestDto.getVehicleModel())
                .vehicleNumber(vehicleRequestDto.getVehicleNumber())
                .vehicleType(vehicleRequestDto.getVehicleType())
                .vehicleName(vehicleRequestDto.getVehicleName())
                .customer(customerSearch)
                .build();
        vehicleRepository.save(vehicle);
        return mapper.toResponse(vehicle);
    }
}
