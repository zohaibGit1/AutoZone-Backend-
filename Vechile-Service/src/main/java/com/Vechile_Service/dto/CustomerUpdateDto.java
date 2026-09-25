package com.Vechile_Service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateDto {

    private String customerName;
    private String phoneNumber;

}
