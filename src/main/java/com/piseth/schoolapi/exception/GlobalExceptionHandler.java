package com.piseth.schoolapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getMessage(), apiException.getHttpStatus());

        return ResponseEntity
                .status(errorResponse.getHttpStatus())
                .body(errorResponse);
    }
}
