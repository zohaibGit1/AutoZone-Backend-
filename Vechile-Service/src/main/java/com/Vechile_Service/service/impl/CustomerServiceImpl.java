package com.Vechile_Service.service.impl;

import com.Vechile_Service.dto.CustomerHistoryDto;
import com.Vechile_Service.dto.CustomerRequestDto;
import com.Vechile_Service.dto.CustomerResponseDto;
import com.Vechile_Service.dto.CustomerUpdateDto;
import com.Vechile_Service.entity.Customer;
import com.Vechile_Service.entity.VehicleVisit;
import com.Vechile_Service.exception.ResourceNotFoundException;
import com.Vechile_Service.mapper.Mapper;
import com.Vechile_Service.repo.CustomerRepository;
import com.Vechile_Service.repo.VehicleVisitRepository;
import com.Vechile_Service.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

        private final CustomerRepository customerRepository;
        private final Mapper customerMapper;
        private final VehicleVisitRepository vehicleVisitRepository;

        public CustomerServiceImpl(CustomerRepository customerRepository, Mapper customerMapper,
                        VehicleVisitRepository vehicleVisitRepository) {
                this.customerRepository = customerRepository;
                this.customerMapper = customerMapper;
                this.vehicleVisitRepository = vehicleVisitRepository;
        }

        @Override
        public CustomerResponseDto registerCustomer(CustomerRequestDto requestDto) {
                Customer customer = Customer.builder()
                                .customerName(requestDto.getCustomerName())
                                .customerEmail(requestDto.getCustomerEmail().trim().toLowerCase(Locale.ROOT))
                                .customerPhone(requestDto.getCustomerPhone().trim())
                                .build();
                return customerMapper.toResponse(customerRepository.save(customer));
        }

        @Override
        public CustomerHistoryDto getCustomerVehicleHistory(String search) {

                String normalizedSearch = search == null ? "" : search.trim();
                Optional<Customer> customer;

                if (normalizedSearch.contains("@")) {
                        customer = customerRepository.findByCustomerEmailIgnoreCase(normalizedSearch);
                } else {
                        String normalizedPhone = normalizedSearch.replaceAll("\\D", "");
                        if (normalizedPhone.length() == 12 && normalizedPhone.startsWith("91")) {
                                normalizedPhone = normalizedPhone.substring(2);
                        }
                        customer = customerRepository.findByCustomerPhone(normalizedPhone);
                }

                Customer customerData = customer.orElseThrow(
                                () -> new ResourceNotFoundException("Customer not found " + normalizedSearch));

                List<VehicleVisit> visits = vehicleVisitRepository.findByVehicleCustomerCustomerId(
                                customerData.getCustomerId());

                return CustomerHistoryDto.builder()
                                .customerId(customerData.getCustomerId())
                                .customerName(customerData.getCustomerName())
                                .customerEmail(customerData.getCustomerEmail())
                                .customerPhone(customerData.getCustomerPhone())
                                .vehicles(
                                                customerData.getVehicles()
                                                                .stream()
                                                                .map(customerMapper::toResponse)
                                                                .toList())
                                .vehicleVisits(
                                                visits.stream()
                                                                .map(customerMapper::toHistoryResponse)
                                                                .toList())
                                .build();
        }

        @Override
        public CustomerResponseDto updateCustomer(Long customerId, CustomerUpdateDto updateDto) {
                Customer customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
                customer.setCustomerName(updateDto.getCustomerName());
                customer.setCustomerPhone(updateDto.getPhoneNumber());
                return customerMapper.toResponse(customerRepository.save(customer));
        }

}
