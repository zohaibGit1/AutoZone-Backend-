package com.Vechile_Service.dto;

import com.Vechile_Service.constant.VehicleType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponseDto {
    private Long vehicleId;
    private String vehicleNumber;
    private String vehicleName;
    private String vehicleModel;
    private VehicleType vehicleType;
    private Long customerId;
}
