package com.Vechile_Service.service.impl;

import com.Vechile_Service.dto.ComplaintRequestDto;
import com.Vechile_Service.dto.ComplaintResponseDto;
import com.Vechile_Service.entity.Complaint;
import com.Vechile_Service.entity.VehicleVisit;
import com.Vechile_Service.exception.ResourceNotFoundException;
import com.Vechile_Service.mapper.Mapper;
import com.Vechile_Service.repo.ComplaintRepository;
import com.Vechile_Service.repo.VehicleVisitRepository;
import com.Vechile_Service.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final VehicleVisitRepository vehicleVisitRepository;
    private final Mapper mapper;

    @Override
    public ComplaintResponseDto registerComplaint(
            ComplaintRequestDto complaintRequest) {

        VehicleVisit vehicleVisit = vehicleVisitRepository
                .findById(complaintRequest.getVehicleVisitId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle visit not found with id "
                                        + complaintRequest.getVehicleVisitId()
                        ));

        Complaint complaint = Complaint.builder()
                .complaintDescription(complaintRequest.getComplaintDescription())
                .vehicleVisit(vehicleVisit)
                .build();
        return mapper.toResponseDto(complaintRepository.save(complaint));
    }
}
