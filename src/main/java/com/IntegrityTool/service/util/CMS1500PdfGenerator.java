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
        public static List<Map<String, Object>> generateClaimForm(Map<String, Object> data, String fileName)
                        throws Exception {
                List<Map<String, Object>> result = new ArrayList<>();
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

                if (data.containsKey("date"))
                        addRow(table, "BHT Date", data.get("date").toString()); // Used "BHT Date" for clarity

                // "transactionSetConventionReference":"005010X222A1"
                if (data.containsKey("transactionSetConventionReference"))
                        addRow(table, "Transaction Set Convention Reference",
                                        data.get("transactionSetConventionReference").toString());

                // "insuredCountryCode":"CA"
                if (data.containsKey("insuredCountryCode"))
                        addRow(table, "Insured Country Code", data.get("insuredCountryCode").toString());

                // "claimFilingCode":""
                if (data.containsKey("claimFilingCode"))
                        addRow(table, "Claim Filing Code", data.get("claimFilingCode").toString());

                // "gender":"M"
                if (data.containsKey("gender"))
                        addRow(table, "Gender", data.get("gender").toString());

                // "functionalGroupTime":"1118"
                if (data.containsKey("functionalGroupTime"))
                        addRow(table, "Functional Group Time", data.get("functionalGroupTime").toString());

                // "policyNumber":""
                if (data.containsKey("policyNumber"))
                        addRow(table, "Policy Number", data.get("policyNumber").toString());

                // "transactionSetPurposeCode":"00"
                if (data.containsKey("transactionSetPurposeCode"))
                        addRow(table, "Transaction Set Purpose Code", data.get("transactionSetPurposeCode").toString());

                // "functionalGroupReceiver":"RECEIVERSID"
                if (data.containsKey("functionalGroupReceiver"))
                        addRow(table, "Functional Group Receiver", data.get("functionalGroupReceiver").toString());

                // "transactionTypeCode":"CH"
                if (data.containsKey("transactionTypeCode"))
                        addRow(table, "Transaction Type Code", data.get("transactionTypeCode").toString());

                // "insuranceSequenceNumber":"P"
                if (data.containsKey("insuranceSequenceNumber"))
                        addRow(table, "Insurance Sequence Number", data.get("insuranceSequenceNumber").toString());

                // "interchangeControlNumber":"000000001"
                if (data.containsKey("interchangeControlNumber"))
                        addRow(table, "Interchange Control Number", data.get("interchangeControlNumber").toString());

                // "referenceIdentification":"A71850361" - This seems to be from the BHT
                // segment's Reference Identification (elements[3])
                if (data.containsKey("referenceIdentification"))
                        addRow(table, "BHT Reference Identification", data.get("referenceIdentification").toString());

                // "interchangeReceiverId":"RECEIVERID"
                if (data.containsKey("interchangeReceiverId"))
                        addRow(table, "Interchange Receiver ID", data.get("interchangeReceiverId").toString());

                // "responsibleAgencyCode":"X"
                if (data.containsKey("responsibleAgencyCode"))
                        addRow(table, "Responsible Agency Code", data.get("responsibleAgencyCode").toString());

                // "hierarchicalStructureCode":"0019"
                if (data.containsKey("hierarchicalStructureCode"))
                        addRow(table, "Hierarchical Structure Code", data.get("hierarchicalStructureCode").toString());

                // "patientAddress":"SUBMITTER STREET ADDRESS" - Note: This might be incorrect
                // mapping based on typical 837 (Submitter address vs Patient address)
                if (data.containsKey("patientAddress"))
                        addRow(table, "Patient Address", data.get("patientAddress").toString());

                // "patientCity":"SUBMITTER CITY" - Note: This might be incorrect mapping based
                // on typical 837 (Submitter city vs Patient city)
                if (data.containsKey("patientCity"))
                        addRow(table, "Patient City", data.get("patientCity").toString());

                // "insurancePlanName":""
                if (data.containsKey("insurancePlanName"))
                        addRow(table, "Insurance Plan Name", data.get("insurancePlanName").toString());

                // "insuranceTypeCode":"18" - Note: This is likely from SBR element 2, not 1 as
                // in your earlier code
                if (data.containsKey("insuranceTypeCode"))
                        addRow(table, "Insurance Type Code", data.get("insuranceTypeCode").toString());

                // "functionalGroupSender":"SUBMITTERSID"
                if (data.containsKey("functionalGroupSender"))
                        addRow(table, "Functional Group Sender", data.get("functionalGroupSender").toString());

                // "interchangeDate":"250419"
                if (data.containsKey("interchangeDate"))
                        addRow(table, "Interchange Date", data.get("interchangeDate").toString());

                // "acknowledgmentRequested":"0"
                if (data.containsKey("acknowledgmentRequested"))
                        addRow(table, "Acknowledgment Requested", data.get("acknowledgmentRequested").toString());

                // "insuredState":"QC"
                if (data.containsKey("insuredState"))
                        addRow(table, "Insured State", data.get("insuredState").toString());

                // "patientCountryCode":"CA"
                if (data.containsKey("patientCountryCode"))
                        addRow(table, "Patient Country Code", data.get("patientCountryCode").toString());

                // "functionalGroupDate":"20250419"
                if (data.containsKey("functionalGroupDate"))
                        addRow(table, "Functional Group Date", data.get("functionalGroupDate").toString());

                // "groupNumber":""
                if (data.containsKey("groupNumber"))
                        addRow(table, "Group Number", data.get("groupNumber").toString());

                // "insuredCity":"BILLING PROVIDER CITY" - Note: This mapping seems incorrect
                // (Insured city vs Billing Provider city)
                if (data.containsKey("insuredCity"))
                        addRow(table, "Insured City", data.get("insuredCity").toString());

                // "interchangeSenderId":"SUBMITTERID"
                if (data.containsKey("interchangeSenderId"))
                        addRow(table, "Interchange Sender ID", data.get("interchangeSenderId").toString());

                // "functionalGroupControlNumber":"1"
                if (data.containsKey("functionalGroupControlNumber"))
                        addRow(table, "Functional Group Control Number",
                                        data.get("functionalGroupControlNumber").toString());

                // "dob":"19801026"
                if (data.containsKey("dob"))
                        addRow(table, "Patient DOB", data.get("dob").toString()); // Using "Patient DOB"

                // "transactionSetId":"837"
                if (data.containsKey("transactionSetId"))
                        addRow(table, "Transaction Set ID", data.get("transactionSetId").toString());

                // "insuredZip":"H2H2H2" - Note: This mapping seems incorrect (Insured zip vs
                // Billing Provider zip)
                if (data.containsKey("insuredZip"))
                        addRow(table, "Insured Zip", data.get("insuredZip").toString());

                // "time":"1118" - This seems to be from the BHT segment's time (elements[5])
                if (data.containsKey("time"))
                        addRow(table, "BHT Time", data.get("time").toString()); // Using "BHT Time" for clarity

                // "patientState":"QC"
                if (data.containsKey("patientState"))
                        addRow(table, "Patient State", data.get("patientState").toString());

                // "interchangeTime":"1118"
                if (data.containsKey("interchangeTime"))
                        addRow(table, "Interchange Time", data.get("interchangeTime").toString());

                // "versionReleaseIndustryCode":"005010X222A1"
                if (data.containsKey("versionReleaseIndustryCode"))
                        addRow(table, "Version Release Industry Code",
                                        data.get("versionReleaseIndustryCode").toString());

                if (data.containsKey("controlNumber"))
                        addRow(table, "ST Control Number", data.get("controlNumber").toString());

                // "patientZip":"H1H1H1"
                if (data.containsKey("patientZip"))
                        addRow(table, "Patient Zip", data.get("patientZip").toString());

                if (data.containsKey("submitterName"))
                        addRow(table, "Submitter Name", data.get("submitterName").toString());
                if (data.containsKey("submitterId"))
                        addRow(table, "Submitter ID", data.get("submitterId").toString());
                if (data.containsKey("receiverName"))
                        addRow(table, "Receiver Name", data.get("receiverName").toString());
                if (data.containsKey("receiverId"))
                        addRow(table, "Receiver ID", data.get("receiverId").toString());
                if (data.containsKey("billingProviderName"))
                        addRow(table, "Billing Provider Name", data.get("billingProviderName").toString());
                if (data.containsKey("billingProviderNPI"))
                        addRow(table, "Billing Provider NPI", data.get("billingProviderNPI").toString());
                if (data.containsKey("insuredName"))
                        addRow(table, "Insured Name", data.get("insuredName").toString());
                if (data.containsKey("insuredId"))
                        addRow(table, "Insured ID", data.get("insuredId").toString());
                if (data.containsKey("patientName"))
                        addRow(table, "Patient Name", data.get("patientName").toString());
                if (data.containsKey("serviceDate"))
                        addRow(table, "Service Date", data.get("serviceDate").toString());
                if (data.containsKey("diagnosisCodes"))
                        addRow(table, "Diagnosis Codes", data.get("diagnosisCodes").toString());
                if (data.containsKey("claimNumber"))
                        addRow(table, "Claim Number", data.get("claimNumber").toString());
                if (data.containsKey("claimAmount"))
                        addRow(table, "Claim Amount", "$" + data.get("claimAmount").toString());
                if (data.containsKey("relationshipToInsured"))
                        addRow(table, "Relationship to Insured", data.get("relationshipToInsured").toString());

                document.add(table);
                document.close();

                byte[] pdfBytes = baos.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDisposition(ContentDisposition.inline()
                                .filename(fileName)
                                .build());

                Map<String, Object> resultSet = new HashMap<>();
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
