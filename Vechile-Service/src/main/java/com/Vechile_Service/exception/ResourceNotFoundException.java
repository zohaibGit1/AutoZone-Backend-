package com.Vechile_Service.exception;

import com.Vechile_Service.entity.Customer;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
