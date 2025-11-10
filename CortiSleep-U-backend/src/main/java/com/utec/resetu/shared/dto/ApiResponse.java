package com.utec.resetu.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiResponse<T> {
    public ApiResponse() {}
    public ApiResponse(boolean success, String message, T data, java.time.LocalDateTime timestamp) {
        this.success = success; this.message = message; this.data = data; this.timestamp = timestamp;
    }
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }
}