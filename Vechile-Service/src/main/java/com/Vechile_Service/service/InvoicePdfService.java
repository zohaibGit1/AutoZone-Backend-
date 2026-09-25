package com.Vechile_Service.service;

import com.Vechile_Service.entity.Invoice;
import com.Vechile_Service.entity.InvoiceItem;

import java.io.IOException;
import java.util.List;

public interface InvoicePdfService {
    byte[] generateInvoicePdf(
            Invoice invoice,
            List<InvoiceItem> invoiceItems
    ) throws IOException;
}
