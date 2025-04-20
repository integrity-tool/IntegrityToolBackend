package com.IntegrityTool.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class CMS1500PdfGenerator {
    public static List<Map<String,Object>> generateClaimForm(Map<String, Object> data, String fileName) throws Exception {
        List<Map<String,Object>> result = new ArrayList<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("HEALTH INSURANCE CLAIM FORM").setBold().setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(fileName).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        Table table = new Table(UnitValue.createPercentArray(new float[] { 30, 70 })).useAllAvailableWidth();
        table.setWidth(UnitValue.createPercentValue(100));

        addRow(table, "1. Patient Name", data.get("patientName").toString());
        addRow(table, "2. Patient DOB", data.get("dob").toString());
        addRow(table, "3. Gender", data.get("gender").toString());
        addRow(table, "4. Patient Address", data.get("patientAddress") + ", " + data.get("patientCity") + ", "
                + data.get("patientState") + " " + data.get("patientZip"));
        addRow(table, "5. Relationship to Insured", data.get("relationshipToInsured").toString());
        addRow(table, "6. Insured Name", data.get("insuredName").toString());
        addRow(table, "7. Insured ID", data.get("insuredId").toString());
        addRow(table, "8. Insured Address", data.get("insuredAddress") + ", " + data.get("insuredCity") + ", "
                + data.get("insuredState") + " " + data.get("insuredZip"));
        addRow(table, "9. Insurance Type Code", data.get("insuranceTypeCode").toString());
        addRow(table, "10. Group Number", data.get("groupNumber").toString());
        addRow(table, "11. Claim Number", data.get("claimNumber").toString());
        addRow(table, "12. Claim Amount", "$" + data.get("claimAmount").toString());
        addRow(table, "13. Billing Provider", data.get("billingProviderName").toString());
        addRow(table, "14. Billing Provider NPI", data.get("billingProviderNPI").toString());
        addRow(table, "15. Receiver Name", data.get("receiverName").toString());
        addRow(table, "16. Receiver ID", data.get("receiverId").toString());
        addRow(table, "17. Submitter Name", data.get("submitterName").toString());
        addRow(table, "18. Submitter ID", data.get("submitterId").toString());
        addRow(table, "19. Service Date", data.get("serviceDate").toString());
        addRow(table, "20. Diagnosis Codes", data.get("diagnosisCodes").toString());

        document.add(table);
        document.close();

        byte[] pdfBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename(fileName)
                .build());

        Map<String,Object> resultSet = new HashMap<>();
        resultSet.put("pdfDocument", headers);
        resultSet.put("byteData", pdfBytes);
        result.add(resultSet);
        return result;
    }

    private static void addRow(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(value != null ? value : "")).setBorder(Border.NO_BORDER));
    }
}
