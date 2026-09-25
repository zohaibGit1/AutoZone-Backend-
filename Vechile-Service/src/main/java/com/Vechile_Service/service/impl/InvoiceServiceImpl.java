package com.Vechile_Service.service.impl;

import com.Vechile_Service.constant.PaymentStatus;
import com.Vechile_Service.dto.*;
import com.Vechile_Service.entity.Invoice;
import com.Vechile_Service.entity.InvoiceItem;
import com.Vechile_Service.entity.VehicleVisit;
import com.Vechile_Service.exception.DuplicateResourceException;
import com.Vechile_Service.exception.ResourceNotFoundException;
import com.Vechile_Service.mapper.Mapper;
import com.Vechile_Service.repo.InvoiceItemRepository;
import com.Vechile_Service.repo.InvoiceRepository;
import com.Vechile_Service.repo.VehicleVisitRepository;
import com.Vechile_Service.service.InvoicePdfService;
import com.Vechile_Service.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class InvoiceServiceImpl implements InvoiceService {

    private final VehicleVisitRepository vehicleVisitRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final Mapper mapper;
    private final InvoicePdfService invoicePdfService;

    public InvoiceServiceImpl(VehicleVisitRepository vehicleVisitRepository, InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository, Mapper mapper, InvoicePdfService invoicePdfService) {
        this.vehicleVisitRepository = vehicleVisitRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.mapper = mapper;
        this.invoicePdfService = invoicePdfService;
    }

    @Override
    @Transactional
    public InvoiceResponseDto createInvoice(InvoiceRequestDto request) {
        if (invoiceRepository.existsByVehicleVisitVisitId(request.getVehicleVisitId())) {
            throw new DuplicateResourceException(
                    "Invoice already exists for vehicle visit "
                            + request.getVehicleVisitId()
            );
        }

        VehicleVisit vehicleVisit = vehicleVisitRepository
                .findById(request.getVehicleVisitId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle visit not found with id "
                                        + request.getVehicleVisitId()
                        ));

        // Calculate Invoice
        InvoiceCalculationResponseDto calculation = calculateInvoice(request);

        // Create the Invoice
        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .vehicleVisit(vehicleVisit)
                .createdAt(LocalDateTime.now())
                .discount(calculation.getDiscount())
                .subtotal(calculation.getSubtotal())
                .taxAmount(calculation.getTaxAmount())
                .taxPercentage(calculation.getTaxPercentage())
                .grandTotal(calculation.getGrandTotal())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        // Saved Invoice in the database
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // 6. Create Invoice Items
        List<InvoiceItem> invoiceItems =
                calculation.getItems()
                        .stream()
                        .map(item -> InvoiceItem.builder()
                                .invoice(savedInvoice)
                                .description(item.getDescription())
                                .itemType(item.getItemType())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .totalPrice(item.getTotalPrice())
                                .build())
                        .toList();

        invoiceItemRepository.saveAll(invoiceItems);

        // 7. Return response
        return mapper.toInvoiceResponse(savedInvoice, invoiceItems);

    }

    private String generateInvoiceNumber() {
        return "INV-"+ LocalDate.now()
                .toString()
                .substring(0, 8)
                .toUpperCase()
                +"-"+ new Random().nextInt(10000);
    }

    @Override
    public InvoiceResponseDto getInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Invoice not found with id " + invoiceId
                ));
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findByInvoiceInvoiceId(invoiceId);
        return mapper.toInvoiceResponse(invoice, invoiceItemList);
    }

    @Override
        public InvoiceCalculationResponseDto calculateInvoice(
                InvoiceRequestDto request) {

            // 1. Validate Vehicle Visit
            vehicleVisitRepository
                    .findById(request.getVehicleVisitId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Vehicle visit not found with id "
                                            + request.getVehicleVisitId()
                            ));

            // 2. Calculate item totals
            List<InvoiceItemResponseDto> items = request.getItems()
                    .stream()
                    .map(item -> {

                        BigDecimal totalPrice =
                                item.getUnitPrice()
                                        .multiply(
                                                BigDecimal.valueOf(
                                                        item.getQuantity()
                                                )
                                        );

                        return InvoiceItemResponseDto.builder()
                                .description(item.getDescription())
                                .itemType(item.getItemType())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .totalPrice(totalPrice)
                                .build();
                    })
                    .toList();

            // 3. Calculate subtotal
            BigDecimal subtotal = items.stream()
                    .map(InvoiceItemResponseDto::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 4. Discount
            BigDecimal discount = request.getDiscount() != null
                    ? request.getDiscount()
                    : BigDecimal.ZERO;

            BigDecimal taxableAmount = subtotal.subtract(discount);

            // 5. Tax
            BigDecimal taxPercentage = request.getTaxPercentage() != null
                    ? request.getTaxPercentage()
                    : BigDecimal.ZERO;

            BigDecimal taxAmount = taxableAmount
                    .multiply(taxPercentage)
                    .divide(BigDecimal.valueOf(100));

            // 6. Grand total
            BigDecimal grandTotal = taxableAmount.add(taxAmount);

            // 7. Return calculation
            return InvoiceCalculationResponseDto.builder()
                    .subtotal(subtotal)
                    .discount(discount)
                    .taxableAmount(taxableAmount)
                    .taxAmount(taxAmount)
                    .taxPercentage(taxPercentage)
                    .grandTotal(grandTotal)
                    .items(items)
                    .build();
        }

    @Override
    public byte[] generateInvoicePdf(Long invoiceId) throws IOException {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Invoice not found with id " + invoiceId
                ));

        List<InvoiceItem> invoiceItems =
                invoiceItemRepository.findByInvoiceInvoiceId(invoiceId);

        return invoicePdfService.generateInvoicePdf(
                invoice,
                invoiceItems
        );
    }

    @Override
    @Transactional
    public InvoiceResponseDto updatePayment(
            Long invoiceId,
            PaymentRequestDto request) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invoice not found with id " + invoiceId
                        ));

        if (invoice.getPaymentStatus() == PaymentStatus.SUCCESSFUL) {
            throw new IllegalStateException(
                    "Invoice payment is already completed"
            );
        }

        invoice.setPaymentMethod(request.getPaymentMethod());
        invoice.setPaymentStatus(PaymentStatus.SUCCESSFUL);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceItem> invoiceItems =
                invoiceItemRepository.findByInvoiceInvoiceId(invoiceId);

        return mapper.toInvoiceResponse(savedInvoice, invoiceItems);
    }
}

