package com.IntegrityTool.model.EdiFile;

import java.sql.Timestamp;

public class EdiFile {
    private String fileType;
    private String fileName;
    private Long fileSize;
    private String filePath;
    private Timestamp createdTimeStamp;
    private byte[] originalHash;
    private String backupFilePath;
    private String integrityStatus;
    private String userId;

    public EdiFile(String fileType, String fileName, Long fileSize, String filePath, byte[] originalHash,String backupFilePath, String integrityStatus, String userId) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.createdTimeStamp = new Timestamp(System.currentTimeMillis());
        this.originalHash = originalHash;
        this.backupFilePath = backupFilePath;
        this.integrityStatus = integrityStatus;
        this.userId = userId;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public Timestamp getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public byte[] getOriginalHash() {
        return originalHash;
    }

    public String getBackupFilePath() {
        return backupFilePath;
    }

    public String getIntegrityStatus() {
        return integrityStatus;
    }

    public String getUserId() {
        return userId;
    }

}
