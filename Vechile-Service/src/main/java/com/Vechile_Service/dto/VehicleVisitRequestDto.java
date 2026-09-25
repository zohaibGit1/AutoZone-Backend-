package com.Vechile_Service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleVisitRequestDto {

    @NotNull(message = "Current KM is required")
    @Min(value = 0, message = "Current KM cannot be negative")
    private Integer currentKm;

    @NotNull(message = "Vehicle ID is required")
    @Positive(message = "Vehicle ID must be positive")
    private Long vehicleId;
}
