package com.IntegrityTool.model.EdiFile;

import java.io.InputStream;
import java.security.Timestamp;
import java.util.UUID;

import com.IntegrityTool.model.Patient.Patient;

public class EdiFile {
    private String fileId;
    private String fileType;
    private String fileName;
    private String filePath;
    private InputStream fileInputStream;
    private Timestamp createdAt;
    private String originalHash;
    private String backupFilePath;
    private String integrityStatus;
    private Patient patient;

    public EdiFile(String fileId, String fileType, String fileName, String filePath, InputStream fileInputStream,Timestamp createdAt, String originalHash, String backupFilePath, String integrityStatus, Patient patient) {
        this.fileId = UUID.randomUUID().toString();
        this.fileType = fileType;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileInputStream = fileInputStream;
        this.createdAt = createdAt;
        this.originalHash = originalHash;
        this.backupFilePath = backupFilePath;
        this.integrityStatus = integrityStatus;
        this.patient = patient;
    }
}
