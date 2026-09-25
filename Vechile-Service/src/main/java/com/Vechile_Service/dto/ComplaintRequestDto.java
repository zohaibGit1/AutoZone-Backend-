package com.Vechile_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintRequestDto {

    @NotBlank(message = "Complaint description is required")
    private String complaintDescription;

    @NotNull(message = "Vehicle visit ID is required")
    @Positive(message = "Vehicle visit ID must be positive")
    private Long vehicleVisitId;
}
