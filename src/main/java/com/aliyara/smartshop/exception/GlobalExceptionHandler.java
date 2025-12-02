package com.aliyara.smartshop.exception;

import com.aliyara.smartshop.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResource(DuplicateResourceException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ExpiredPromoCodeException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredPromoCode(ExpiredPromoCodeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, "An unexpected error occurred: " + ex.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
