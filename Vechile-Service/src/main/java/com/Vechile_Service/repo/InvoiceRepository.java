package com.Vechile_Service.repo;

import com.Vechile_Service.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByVehicleVisitVisitId(Long visitId);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    boolean existsByVehicleVisitVisitId(Long id);
}
