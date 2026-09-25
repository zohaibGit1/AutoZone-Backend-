package com.Vechile_Service.service;

import com.Vechile_Service.dto.InvoiceCalculationResponseDto;
import com.Vechile_Service.dto.InvoiceRequestDto;
import com.Vechile_Service.dto.InvoiceResponseDto;
import com.Vechile_Service.dto.PaymentRequestDto;

import java.io.IOException;

public interface InvoiceService {

    // Final bill create/generate
    InvoiceResponseDto createInvoice(InvoiceRequestDto request);

    // Existing invoice details
    InvoiceResponseDto getInvoice(Long invoiceId);

    // Calculate total before final invoice creation
    InvoiceCalculationResponseDto calculateInvoice(InvoiceRequestDto request);

    // Generate/download PDF
    byte[] generateInvoicePdf(Long invoiceId) throws IOException;

    // Payment update
    InvoiceResponseDto updatePayment(
            Long invoiceId,
            PaymentRequestDto request
    );
}