package com.IntegrityTool.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EdiParserService {
    public Map<String, Object> parseEdiData(List<String> segments) {
        List<String> result = new ArrayList<>();
        Map<String, Object> parsedData = new HashMap<>();

        for (String segment : segments) {
            String[] elements = segment.split("\\*");
            String segmentId = elements[0];

            // Assuming 'elements' is a String array containing the elements of the current
            // segment
            // Assuming 'segmentId' is the ID of the current segment (e.g., "ISA", "GS",
            // "NM1")
            // Assuming 'parsedData' is a Map<String, String> to store the extracted data
            // Assuming 'result' is another list you are using for something else (like SV1
            // details)

            switch (segmentId) {
                case "ISA":
                    parsedData.put("interchangeSenderId", elements[6].trim());
                    parsedData.put("interchangeReceiverId", elements[8].trim());
                    // Add Interchange Date and Time
                    parsedData.put("interchangeDate", elements[9].trim());
                    parsedData.put("interchangeTime", elements[10].trim());
                    // Add Interchange Control Number
                    parsedData.put("interchangeControlNumber", elements[13].trim());
                    // Add Acknowledgment Requested
                    parsedData.put("acknowledgmentRequested", elements[14].trim()); // Typically '0' or '1'
                    break;

                case "GS":
                    parsedData.put("functionalGroupSender", elements[2].trim());
                    parsedData.put("functionalGroupReceiver", elements[3].trim());
                    // Add Functional Group Date and Time
                    parsedData.put("functionalGroupDate", elements[4].trim());
                    parsedData.put("functionalGroupTime", elements[5].trim());
                    // Add Functional Group Control Number
                    parsedData.put("functionalGroupControlNumber", elements[6].trim());
                    // Add Responsible Agency Code
                    parsedData.put("responsibleAgencyCode", elements[7].trim());
                    // Add Version / Release / Industry Identifier Code
                    parsedData.put("versionReleaseIndustryCode", elements[8].trim());
                    break;

                case "ST":
                    parsedData.put("transactionSetId", elements[1].trim());
                    parsedData.put("controlNumber", elements[2].trim());
                    // The ST segment might have a third element in some versions (Transaction Set
                    // Implementation Convention Reference)
                    if (elements.length > 3) {
                        parsedData.put("transactionSetConventionReference", elements[3].trim());
                    }
                    break;

                case "BHT": // Beginning of Hierarchical Transaction
                    // Typically appears after ST. Contains information about the transaction
                    // purpose and date.
                    if (elements.length > 1) {
                        parsedData.put("hierarchicalStructureCode", elements[1].trim()); // e.g., 0019
                    }
                    if (elements.length > 2) {
                        parsedData.put("transactionSetPurposeCode", elements[2].trim()); // e.g., 00 for original
                    }
                    if (elements.length > 3) {
                        parsedData.put("referenceIdentification", elements[3].trim()); // e.g., a claim number
                    }
                    if (elements.length > 4) {
                        parsedData.put("date", elements[4].trim()); // Transaction date
                    }
                    if (elements.length > 5) {
                        parsedData.put("time", elements[5].trim()); // Transaction time
                    }
                    if (elements.length > 6) {
                        parsedData.put("transactionTypeCode", elements[6].trim()); // e.g., CH for charge
                    }
                    break;

                case "NM1":
                    // This segment is used for many different names and entities based on
                    // element[1]
                    String entityIdentifierCode = elements[1];
                    if ("41".equals(entityIdentifierCode)) { // Submitter
                        parsedData.put("submitterName", elements[3].trim());
                        parsedData.put("submitterIdCodeQualifier", elements[8].trim()); // e.g., 46 for NPI
                        parsedData.put("submitterId", elements[9].trim());
                    } else if ("40".equals(entityIdentifierCode)) { // Receiver
                        parsedData.put("receiverName", elements[3].trim());
                        parsedData.put("receiverIdCodeQualifier", elements[8].trim()); // e.g., 46 for NPI
                        parsedData.put("receiverId", elements[9].trim());
                    } else if ("85".equals(entityIdentifierCode)) { // Billing Provider
                        parsedData.put("billingProviderName", elements[3].trim()); // Last Name
                        if (elements.length > 4)
                            parsedData.put("billingProviderFirstName", elements[4].trim());
                        if (elements.length > 5)
                            parsedData.put("billingProviderMiddleName", elements[5].trim());
                        if (elements.length > 7)
                            parsedData.put("billingProviderSuffix", elements[7].trim());
                        parsedData.put("billingProviderIdCodeQualifier", elements[8].trim()); // e.g., PI (Payor
                                                                                              // Identification), SV
                                                                                              // (Servicing Provider
                                                                                              // Number)
                        parsedData.put("billingProviderNPI", elements[9].trim());
                    } else if ("IL".equals(entityIdentifierCode)) { // Insured
                        parsedData.put("insuredLastName", elements[3].trim());
                        if (elements.length > 4)
                            parsedData.put("insuredFirstName", elements[4].trim());
                        if (elements.length > 5)
                            parsedData.put("insuredMiddleName", elements[5].trim());
                        if (elements.length > 7)
                            parsedData.put("insuredSuffix", elements[7].trim());
                        parsedData.put("insuredIdCodeQualifier", elements[8].trim()); // e.g., MI (Member
                                                                                      // Identification)
                        parsedData.put("insuredId", elements[9].trim());
                    } else if ("QC".equals(entityIdentifierCode)) { // Patient
                        parsedData.put("patientLastName", elements[3].trim());
                        if (elements.length > 4)
                            parsedData.put("patientFirstName", elements[4].trim());
                        if (elements.length > 5)
                            parsedData.put("patientMiddleName", elements[5].trim());
                        if (elements.length > 7)
                            parsedData.put("patientSuffix", elements[7].trim());
                        // Patient NM1 might not always have an ID in element 9, but if it does:
                        if (elements.length > 9) {
                            parsedData.put("patientIdCodeQualifier", elements[8].trim());
                            parsedData.put("patientId", elements[9].trim());
                        }
                    } else if ("PR".equals(entityIdentifierCode)) { // Payer (Insurance Company)
                        parsedData.put("payerName", elements[2].trim());
                        parsedData.put("payerIdCodeQualifier", elements[8].trim()); // e.g., PI
                        parsedData.put("payerId", elements[9].trim());
                    }
                    // Add other possible NM1 entity identifier codes as needed (e.g., 87 for
                    // Referring Provider, RPE for Rendering Provider)
                    break;

                case "N3": // Address Information (Street Address)
                    // N3 is used for addresses of various entities. The context (which loop it's
                    // in) determines whose address it is.
                    // You've handled patient and insured address based on a check if the key
                    // exists, which might work
                    // but is not the standard way. A better approach would be to handle N3 within
                    // its loop processing.
                    // For a simple flat structure, this might be sufficient if N3 for patient
                    // always comes before insured.
                    if (!parsedData.containsKey("patientAddress")) {
                        parsedData.put("patientAddress", elements[1].trim());
                        if (elements.length > 2)
                            parsedData.put("patientAddressLine2", elements[2].trim());
                    } else if (!parsedData.containsKey("insuredAddress")) {
                        parsedData.put("insuredAddress", elements[1].trim());
                        if (elements.length > 2)
                            parsedData.put("insuredAddressLine2", elements[2].trim());
                    }
                    // You would similarly add logic for Billing Provider Address if needed, likely
                    // requiring loop awareness.
                    break;

                case "N4": // Geographic Location (City, State, Zip)
                    // Similar to N3, context matters.
                    if (!parsedData.containsKey("patientCity")) {
                        parsedData.put("patientCity", elements[1].trim());
                        if (elements.length > 2)
                            parsedData.put("patientState", elements[2].trim());
                        if (elements.length > 3)
                            parsedData.put("patientZip", elements[3].trim());
                        if (elements.length > 4)
                            parsedData.put("patientCountryCode", elements[4].trim());
                    } else if (!parsedData.containsKey("insuredCity")) {
                        parsedData.put("insuredCity", elements[1].trim());
                        if (elements.length > 2)
                            parsedData.put("insuredState", elements[2].trim());
                        if (elements.length > 3)
                            parsedData.put("insuredZip", elements[3].trim());
                        if (elements.length > 4)
                            parsedData.put("insuredCountryCode", elements[4].trim());
                    }
                    // You would similarly add logic for Billing Provider City/State/Zip if needed.
                    break;

                case "DMG": // Demographic Information
                    // Similar to N3/N4, context matters (Patient vs. Insured)
                    parsedData.put("dob", elements[2].trim()); // Date of Birth
                    parsedData.put("gender", elements[3].trim()); // Gender
                    if (elements.length > 4)
                        parsedData.put("maritalStatus", elements[4].trim()); // Marital Status
                    if (elements.length > 5)
                        parsedData.put("raceOrEthnicity", elements[5].trim()); // Race or Ethnicity
                    break;

                case "PAT": // Patient Information (Generally follows Patient NM1)
                    parsedData.put("relationshipToInsured", elements[1].trim()); // Relationship Code (e.g., 01 for
                                                                                 // Spouse)
                    if (elements.length > 2)
                        parsedData.put("patientWeight", elements[2].trim());
                    if (elements.length > 3)
                        parsedData.put("patientPregnancyIndicator", elements[3].trim());
                    if (elements.length > 4)
                        parsedData.put("patientDeathDate", elements[4].trim());
                    if (elements.length > 5)
                        parsedData.put("patientDeathTime", elements[5].trim());
                    if (elements.length > 6)
                        parsedData.put("patientUnitOfMeasurement", elements[6].trim());
                    if (elements.length > 7)
                        parsedData.put("patientStatusCode", elements[7].trim()); // e.g., A for Active
                    break;

                case "SBR": // Subscriber Information (Policyholder)
                    parsedData.put("insuranceSequenceNumber", elements[1].trim()); // Payer Sequence Number (e.g., P for
                                                                                   // Primary)
                    if (elements.length > 2)
                        parsedData.put("insuranceTypeCode", elements[2].trim()); // Insurance Type Code (e.g., C for
                                                                                 // Commercial)
                    parsedData.put("groupNumber", elements[3].trim()); // Group Number
                    if (elements.length > 4)
                        parsedData.put("policyNumber", elements[4].trim()); // Policy Number (Subscriber ID)
                    if (elements.length > 5)
                        parsedData.put("insurancePlanName", elements[5].trim()); // Insurance Plan Name
                    if (elements.length > 6)
                        parsedData.put("claimFilingCode", elements[6].trim()); // Claim Filing Indicator Code (e.g., MC
                                                                               // for Medicare)
                    // Add other elements as needed
                    break;

                case "CLM": // Claim Information
                    parsedData.put("claimNumber", elements[1].trim()); // Patient Account Number
                    parsedData.put("claimAmount", elements[2].trim()); // Total Claim Amount
                    if (elements.length > 3)
                        parsedData.put("placeOfServiceCode", elements[3].trim()); // Place of Service Code
                    // Add other elements as needed, CLM has many (e.g., Claim Frequency Type Code,
                    // Provider Signature on File, Assignment or Benefit Indicator)
                    break;

                case "DTP": // Date or Time or Period
                    // This segment is used for various dates (Date of Service, Accident Date, etc.)
                    // element[1] is the Date Time Qualifier, element[2] is the Date Time Format,
                    // element[3] is the Date Time Period
                    String dateTimeQualifier = elements[1];
                    if ("472".equals(dateTimeQualifier)) { // Service Date
                        parsedData.put("serviceDate", elements[3].trim()); // Assuming format in element[2] is known
                    } else if ("439".equals(dateTimeQualifier)) { // Accident Date
                        if (elements.length > 3)
                            parsedData.put("accidentDate", elements[3].trim());
                    } else if ("484".equals(dateTimeQualifier)) { // Last Menstrual Period
                        if (elements.length > 3)
                            parsedData.put("lastMenstrualPeriodDate", elements[3].trim());
                    }
                    // Add other relevant DTP qualifiers as needed based on the transaction type
                    break;

                case "HI": // Health Information
                    // This segment contains diagnosis codes, procedure codes (sometimes), etc.
                    // It can have multiple composite elements, each with multiple sub-elements.
                    // Your current handling captures all elements after the first into a string,
                    // which might not be ideal
                    // if you need individual diagnosis codes.
                    // A more robust approach would parse the composite elements (separated by :)
                    // and sub-elements.
                    // For simplicity with the current structure, keeping the array toString might
                    // be okay for now,
                    // but be aware this is a simplification.
                    if (elements.length > 1) {
                        // Assuming elements[1] and subsequent are diagnosis codes
                        // Standard 837 HI segment has composite elements (e.g.,
                        // HI*BP:D8:0309:D8:V403...)
                        // Parsing composite elements requires splitting by the composite delimiter
                        // (often ':')
                        // For a flat structure, just storing the raw codes might suffice for now.
                        parsedData.put("diagnosisCodesRaw",
                                Arrays.toString(Arrays.copyOfRange(elements, 1, elements.length)));
                    }
                    break;

                case "SV1": // Professional Service
                    // This segment contains details about a single service line.
                    // You are currently adding a formatted string to 'result'. If you want to store
                    // this in 'parsedData'
                    // or process individual service line items, you'll need a different approach
                    // (e.g., a list of maps or objects).
                    // For now, let's add some individual elements to parsedData (this assumes only
                    // one SV1 or you want to capture the last one)
                    if (elements.length > 1) {
                        // Element 1 is a composite: SV1*<Product/Service ID>:<Modifier>:<Modifier>...
                        // Extracting the primary procedure code (first part of the composite)
                        String procedureComposite = elements[1];
                        String[] procedureElements = procedureComposite.split(":");
                        if (procedureElements.length > 0) {
                            parsedData.put("procedureCode", procedureElements[0].trim());
                        }
                        // You would need to extract modifiers similarly if needed
                    }
                    if (elements.length > 2)
                        parsedData.put("serviceLineCharge", elements[2].trim());
                    if (elements.length > 3)
                        parsedData.put("serviceUnitCount", elements[3].trim()); // Number of units
                    if (elements.length > 4)
                        parsedData.put("placeOfService", elements[4].trim()); // Place of Service Code
                    // Add other elements as needed (e.g., Type of Service, Diagnosis Pointer)
                    break;

                case "REF": // Reference Identification
                    // Used for various reference numbers (e.g., Referring Provider ID, Prior
                    // Authorization Number)
                    // Element 1 is the Reference Identification Qualifier, Element 2 is the
                    // Reference Identification
                    if (elements.length > 2) {
                        String refQualifier = elements[1];
                        String referenceIdentification = elements[2].trim();
                        if ("0B".equals(refQualifier)) { // Referring Provider ID
                            parsedData.put("referringProviderId", referenceIdentification);
                        } else if ("G1".equals(refQualifier)) { // Prior Authorization Number
                            parsedData.put("priorAuthorizationNumber", referenceIdentification);
                        }
                        // Add other relevant REF qualifiers as needed
                    }
                    break;

                case "PWK": // Paperwork
                    // Used to indicate attached documentation
                    if (elements.length > 1)
                        parsedData.put("reportTypeCode", elements[1].trim());
                    if (elements.length > 2)
                        parsedData.put("reportTransmissionCode", elements[2].trim());
                    // Add other elements as needed (e.g., Identification Code)
                    break;

                // Add cases for other relevant segments in an 837 transaction:
                // case "PRV": // Provider Information (can be used for Rendering Provider,
                // Attending Provider, etc.)
                // case "ALL": // Allowance (used for adjustments)
                // case "CR1": // Ambulance Transport Information
                // case "CR2": // Spinal Manipulation Service Information
                // case "CRC": // Conditions Indicator
                // case "K3": // File Information (sometimes used for claim notes)
                // case "NTE": // Notes (for claim or service line notes)
                // case "LX": // Transaction Line Number (used to number service lines)
                // case "HCP": // Health Care Pricing (for pricing information)
                // case "AMT": // Monetary Amount (for various amounts like patient
                // responsibility)

                case "GE": // Functional Group Trailer
                    // Contains the number of transaction sets and group control number.
                    if (elements.length > 1)
                        parsedData.put("numberOfTransactionSetsInGroup", elements[1].trim());
                    if (elements.length > 2)
                        parsedData.put("functionalGroupControlNumberTrailer", elements[2].trim()); // Should match GS
                                                                                                   // element 6
                    break;

                case "SE": // Transaction Set Trailer
                    // Contains the number of segments in the transaction set and transaction set
                    // control number.
                    if (elements.length > 1)
                        parsedData.put("numberOfSegmentsInTransaction", elements[1].trim());
                    if (elements.length > 2)
                        parsedData.put("transactionSetControlNumberTrailer", elements[2].trim()); // Should match ST
                                                                                                  // element 2
                    break;

                case "IEA": // Interchange Control Trailer
                    // Contains the number of functional groups and interchange control number.
                    if (elements.length > 1)
                        parsedData.put("numberOfFunctionalGroupsInInterchange", elements[1].trim());
                    if (elements.length > 2)
                        parsedData.put("interchangeControlNumberTrailer", elements[2].trim()); // Should match ISA
                                                                                               // element 13
                    break;

                default:
                    // Handle segments that you don't explicitly need to extract data from, or log
                    // them
                    // System.out.println("Skipping segment: " + segmentId);
                    break;
            }
        }
        return parsedData;
    }
}
