package com.IntegrityTool.DTO;

public class ApiErrorHandler {
    private int errorCode;
    private String description;

    public ApiErrorHandler(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
