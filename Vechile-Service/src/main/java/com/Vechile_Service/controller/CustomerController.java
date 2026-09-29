package com.Vechile_Service.controller;

import com.Vechile_Service.dto.CustomerHistoryDto;
import com.Vechile_Service.dto.CustomerRequestDto;
import com.Vechile_Service.dto.CustomerResponseDto;
import com.Vechile_Service.dto.CustomerUpdateDto;
import com.Vechile_Service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register-customer")
    public ResponseEntity<CustomerResponseDto> registerCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {
        return ResponseEntity.ok(customerService.registerCustomer(requestDto));
    }

    @GetMapping("/history")
    public ResponseEntity<CustomerHistoryDto> getCustomerVehicleHistory(
            @RequestParam String search) {
        return ResponseEntity.ok(
                customerService.getCustomerVehicleHistory(search));
    }

    @PatchMapping("/update-customer/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerUpdateDto updateDto) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, updateDto));
    }

}
