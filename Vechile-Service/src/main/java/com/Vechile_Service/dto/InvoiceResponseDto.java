package com.Vechile_Service.dto;

import com.Vechile_Service.constant.PaymentMethod;
import com.Vechile_Service.constant.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponseDto {

    private Long invoiceId;

    private String invoiceNumber;

    private Long vehicleVisitId;

    private LocalDateTime createdAt;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal taxPercentage;

    private BigDecimal taxAmount;

    private BigDecimal grandTotal;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private List<InvoiceItemResponseDto> items;
}