package com.Vechile_Service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceCalculationResponseDto {
    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal taxableAmount;

    private BigDecimal taxPercentage;

    private BigDecimal taxAmount;

    private BigDecimal grandTotal;

    private List<InvoiceItemResponseDto> items;
}
