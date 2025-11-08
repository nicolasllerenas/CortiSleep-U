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
        ApiResponse<T> r = new ApiResponse<>();
        r.setSuccess(true);
        r.setMessage(message);
        r.setData(data);
        r.setTimestamp(LocalDateTime.now());
        return r;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setSuccess(false);
        r.setMessage(message);
        r.setTimestamp(LocalDateTime.now());
        return r;
    }
}