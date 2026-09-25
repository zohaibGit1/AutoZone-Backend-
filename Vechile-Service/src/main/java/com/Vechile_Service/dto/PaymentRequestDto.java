package com.Vechile_Service.dto;

import com.Vechile_Service.constant.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}