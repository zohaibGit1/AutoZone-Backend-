package com.Vechile_Service.dto;

import com.Vechile_Service.constant.ItemType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItemResponseDto {

    private String description;

    private ItemType itemType;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
