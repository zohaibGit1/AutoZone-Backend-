package com.Vechile_Service.repo;

import com.Vechile_Service.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    List<InvoiceItem> findByInvoiceInvoiceId(Long invoiceId);

}
