package com.Vechile_Service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRequestDto {

    @NotNull(message = "Vehicle visit ID is required")
    @Positive(message = "Vehicle visit ID must be positive")
    private Long vehicleVisitId;

    @NotEmpty(message = "Invoice items are required")
    private List<InvoiceItemRequestDto> items; // Bill ke andar customer ko kaunsi service/part/labour charge kiya ja raha hai, uski details.

    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    private BigDecimal discount;

    @DecimalMin(value = "0.0", message = "Tax percentage cannot be negative")
    private BigDecimal taxPercentage;
}
