package com.Vechile_Service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleVisitResponse {
    private Long visitId;
    private LocalDate visitDate;
    private int currentKm;
    private LocalDateTime checkInTime;
    private Long vehicleId;
}
