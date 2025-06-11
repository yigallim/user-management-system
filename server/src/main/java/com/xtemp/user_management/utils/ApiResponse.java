package com.xtemp.user_management.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String code;
    private String message;
    private T data;
    private Object details;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", "OK", message, data, null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>("error", code, message, null, null);
    }

    public static ApiResponse<Object> error(String code, String message, Object details) {
        return new ApiResponse<>("error", code, message, null, details);
    }
}