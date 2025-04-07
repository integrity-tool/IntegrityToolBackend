package com.IntegrityTool.model.Patient;

import java.sql.Date;
import java.util.ArrayList;

public class Patient {
    private String healthInsuranceNumber;
    private PatientServiceRecord patientServiceRecord;
    private String patientRelationShipToInsured;
    private String patientCondition;
    private Date dateOfCurrentIlleness;
    private Date otheDate;
    private Date datesOfPersonUnableToWork;
    private String referringProvider;
    private Date hospitilizationDate;
    private String additionalClaimInfo;
    private boolean outsideLab;
    private double charges;
    private String natureOfIllenessAccident;
    private boolean medicalResubmissionCode;
    private String originalRefNumber;
    private int priorAuthorizedNumber;
    private String otherHealthBenefits;
    private ArrayList<InsurancePolicy> insurancePolicy;
}
