package com.Vechile_Service.entity;

import com.Vechile_Service.constant.PaymentMethod;
import com.Vechile_Service.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "vehicle_visit_id", unique = true, nullable = false)
    private VehicleVisit vehicleVisit;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private BigDecimal discount;

    private BigDecimal subtotal;

    private BigDecimal taxPercentage;

    private BigDecimal taxAmount;

    private BigDecimal grandTotal;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}
