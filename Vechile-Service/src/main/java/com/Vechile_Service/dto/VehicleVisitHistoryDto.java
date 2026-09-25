package com.Vechile_Service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleVisitHistoryDto {

    private Long visitId;
    private LocalDate visitDate;
    private Integer currentKm;
    private Long vehicleId;
    private String vehicleNumber;
    private List<ComplaintResponseDto> complaints;
}
