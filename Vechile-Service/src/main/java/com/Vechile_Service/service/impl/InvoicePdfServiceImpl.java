package com.Vechile_Service.service.impl;

import com.Vechile_Service.entity.Invoice;
import com.Vechile_Service.entity.InvoiceItem;
import com.Vechile_Service.service.InvoicePdfService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class InvoicePdfServiceImpl implements InvoicePdfService {

        @Override
        public byte[] generateInvoicePdf(
                Invoice invoice,
                List<InvoiceItem> invoiceItems) {

            try (ByteArrayOutputStream outputStream =
                         new ByteArrayOutputStream()) {

                Document document = new Document(PageSize.A4);

                PdfWriter.getInstance(document, outputStream);

                document.open();

                // Title
                Font titleFont = FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        20
                );

                Paragraph title = new Paragraph(
                        "VEHICLE SERVICE INVOICE",
                        titleFont
                );

                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph(" "));

                // Invoice details
                document.add(new Paragraph(
                        "Invoice Number: " + invoice.getInvoiceNumber()
                ));

                document.add(new Paragraph(
                        "Invoice Date: " + invoice.getCreatedAt()
                ));

                document.add(new Paragraph(
                        "Vehicle Visit ID: " +
                                invoice.getVehicleVisit().getVisitId()
                ));

                document.add(new Paragraph(" "));

                // Items table
                PdfPTable table = new PdfPTable(5);

                table.setWidthPercentage(100);

                table.addCell("Description");
                table.addCell("Type");
                table.addCell("Qty");
                table.addCell("Unit Price");
                table.addCell("Total");

                for (InvoiceItem item : invoiceItems) {

                    table.addCell(item.getDescription());
                    table.addCell(item.getItemType().name());
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell(
                            item.getUnitPrice().toString()
                    );
                    table.addCell(
                            item.getTotalPrice().toString()
                    );
                }

                document.add(table);

                document.add(new Paragraph(" "));

                // Amounts
                document.add(new Paragraph(
                        "Subtotal: ₹" + invoice.getSubtotal()
                ));

                document.add(new Paragraph(
                        "Discount: ₹" + invoice.getDiscount()
                ));

                document.add(new Paragraph(
                        "Tax: ₹" + invoice.getTaxAmount()
                ));

                Font totalFont = FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        14
                );

                Paragraph grandTotal = new Paragraph(
                        "Grand Total: ₹" + invoice.getGrandTotal(),
                        totalFont
                );

                grandTotal.setAlignment(Element.ALIGN_RIGHT);

                document.add(grandTotal);

                document.add(new Paragraph(" "));

                document.add(new Paragraph(
                        "Payment Status: " +
                                invoice.getPaymentStatus()
                ));

                document.add(new Paragraph(
                        "Payment Method: " +
                                invoice.getPaymentMethod()
                ));

                document.add(new Paragraph(" "));
                document.add(new Paragraph(
                        "Thank you for choosing our service."
                ));

                document.close();

                return outputStream.toByteArray();

            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to generate invoice PDF", e
                );
            }
        }
}
