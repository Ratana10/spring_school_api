package com.piseth.schoolapi.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException apiException){
        ErrorResponse errorResponse = new ErrorResponse(apiException.getMessage(), apiException.getHttpStatus());

        return ResponseEntity
                .status(errorResponse.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request){
        List<String> errors = new ArrayList<>();
        ex.getAllErrors()
                .forEach(err -> errors.add(err.getDefaultMessage()));
        Map<String ,List<String > > result = new HashMap<>();
        result.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
