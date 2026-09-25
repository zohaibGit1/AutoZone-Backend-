package com.Vechile_Service.dto;

import com.Vechile_Service.constant.ItemType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItemRequestDto {
    @NotBlank(message = "Item description is required")
    @Size(max = 255, message = "Item description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Item type is required")
    private ItemType itemType;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Invalid unit price")
    private BigDecimal unitPrice;
}
