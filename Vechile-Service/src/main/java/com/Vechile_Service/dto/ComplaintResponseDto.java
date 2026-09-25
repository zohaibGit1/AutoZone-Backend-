package com.Vechile_Service.dto;

import com.Vechile_Service.entity.VehicleVisit;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintResponseDto {

    private Long complaintId;
    private String complaintDescription;
    private Long vehicleVisitId;
}