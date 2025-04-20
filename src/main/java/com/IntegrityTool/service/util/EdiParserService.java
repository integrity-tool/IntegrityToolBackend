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

            switch (segmentId) {
                case "ISA":
                    parsedData.put("interchangeSenderId", elements[6].trim());
                    parsedData.put("interchangeReceiverId", elements[8].trim());
                    break;

                case "GS":
                    parsedData.put("functionalGroupSender", elements[2].trim());
                    parsedData.put("functionalGroupReceiver", elements[3].trim());
                    break;

                case "ST":
                    parsedData.put("transactionSetId", elements[1].trim());
                    parsedData.put("controlNumber", elements[2].trim());
                    break;

                case "NM1":
                    if ("41".equals(elements[1])) {
                        parsedData.put("submitterName", elements[3].trim());
                        parsedData.put("submitterId", elements[9].trim());
                    } else if ("40".equals(elements[1])) {
                        parsedData.put("receiverName", elements[3].trim());
                        parsedData.put("receiverId", elements[9].trim());
                    } else if ("85".equals(elements[1])) {
                        parsedData.put("billingProviderName", elements[3].trim());
                        parsedData.put("billingProviderNPI", elements[9].trim());
                    } else if ("IL".equals(elements[1])) {
                        parsedData.put("insuredName", elements[3].trim());
                        parsedData.put("insuredId", elements[9].trim());
                    } else if ("QC".equals(elements[1])) {
                        parsedData.put("patientName", elements[3].trim());
                    }
                    break;

                case "N3":
                    if (!parsedData.containsKey("patientAddress")) {
                        parsedData.put("patientAddress", elements[1].trim());
                    } else if (!parsedData.containsKey("insuredAddress")) {
                        parsedData.put("insuredAddress", elements[1].trim());
                    }
                    break;

                case "N4":
                    if (!parsedData.containsKey("patientCity")) {
                        parsedData.put("patientCity", elements[1].trim());
                        parsedData.put("patientState", elements[2].trim());
                        parsedData.put("patientZip", elements[3].trim());
                    } else if (!parsedData.containsKey("insuredCity")) {
                        parsedData.put("insuredCity", elements[1].trim());
                        parsedData.put("insuredState", elements[2].trim());
                        parsedData.put("insuredZip", elements[3].trim());
                    }
                    break;

                case "DMG":
                    parsedData.put("dob", elements[2].trim());
                    parsedData.put("gender", elements[3].trim());
                    break;

                case "PAT":
                    parsedData.put("relationshipToInsured", elements[1].trim());
                    break;

                case "SBR":
                    parsedData.put("insuranceTypeCode", elements[1].trim());
                    parsedData.put("groupNumber", elements[3].trim());
                    break;

                case "CLM":
                    parsedData.put("claimNumber", elements[1].trim());
                    parsedData.put("claimAmount", elements[2].trim());
                    break;

                case "HI":
                    parsedData.put("diagnosisCodes", Arrays.toString(Arrays.copyOfRange(elements, 1, elements.length)));
                    break;

                case "SV1":
                    result.add("Procedure Code: " + elements[1] + ", Charge: " + elements[2]);
                    break;

                case "DTP":
                    if ("472".equals(elements[1])) {
                        parsedData.put("serviceDate", elements[3]);
                    }
                    break;

                default:
                    break;
            }
        }
        return parsedData;
    }
}
