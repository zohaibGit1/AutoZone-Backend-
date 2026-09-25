package com.Vechile_Service.controller;

import com.Vechile_Service.dto.InvoiceCalculationResponseDto;
import com.Vechile_Service.dto.InvoiceRequestDto;
import com.Vechile_Service.dto.InvoiceResponseDto;
import com.Vechile_Service.dto.PaymentRequestDto;
import com.Vechile_Service.service.InvoiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Validated
public class InvoiceController {

    private final InvoiceService invoiceService;

    // 1. Calculate invoice
    @PostMapping("/calculate")
    public ResponseEntity<InvoiceCalculationResponseDto> calculateInvoice(
            @Valid @RequestBody InvoiceRequestDto request) {

        return ResponseEntity.ok(
                invoiceService.calculateInvoice(request)
        );
    }

    // 2. Create / finalize invoice
    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(
            @Valid @RequestBody InvoiceRequestDto request) {

        InvoiceResponseDto response =
                invoiceService.createInvoice(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 3. Get invoice
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponseDto> getInvoice(
            @PathVariable
            @Positive(message = "Invoice ID must be positive")
            Long invoiceId) {

        return ResponseEntity.ok(
                invoiceService.getInvoice(invoiceId)
        );
    }

    // 4. Update payment
    @PatchMapping("/{invoiceId}/payment")
    public ResponseEntity<InvoiceResponseDto> updatePayment(
            @PathVariable
            @Positive(message = "Invoice ID must be positive")
            Long invoiceId,
            @Valid @RequestBody PaymentRequestDto request) {

        return ResponseEntity.ok(
                invoiceService.updatePayment(invoiceId, request)
        );
    }

    // 5. Generate invoice PDF
    @GetMapping(
            value = "/{invoiceId}/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> generateInvoicePdf(
            @PathVariable
            @Positive(message = "Invoice ID must be positive")
            Long invoiceId) throws IOException {

        byte[] pdf = invoiceService.generateInvoicePdf(invoiceId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice-" + invoiceId + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}