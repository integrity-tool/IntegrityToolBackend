package com.IntegrityTool.DTO;

import java.time.Instant;

public class ApiResponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private T data;
    private Instant timestamp;

    public ApiResponse(String status, int statusCode, String message, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static <T> ApiResponse<T> success(int code, String message, T data) {
        return new ApiResponse<T>("Success", code, message, data);
    }

    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return new ApiResponse<T>("Error", code, message, data);
    }
}
