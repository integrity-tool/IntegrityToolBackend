package com.IntegrityTool.model.EdiFile;

public class EdiTask {
    private final byte[] payload;
    private final String fileName;

    public EdiTask(byte[] payload, String fileName) {
        this.payload = payload;
        this.fileName = fileName;
    }

    public byte[] getPayload() {
        return payload;
    }

    public String getFileName() {
        return fileName;
    }
}
