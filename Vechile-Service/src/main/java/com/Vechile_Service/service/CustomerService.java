package com.Vechile_Service.service;

import com.Vechile_Service.dto.CustomerHistoryDto;
import com.Vechile_Service.dto.CustomerRequestDto;
import com.Vechile_Service.dto.CustomerResponseDto;
import com.Vechile_Service.dto.CustomerUpdateDto;

public interface CustomerService {
    CustomerResponseDto registerCustomer(CustomerRequestDto requestDto);
    CustomerHistoryDto getCustomerVehicleHistory(String search);
    CustomerResponseDto updateCustomer(Long customerId, CustomerUpdateDto updateDto);
}
