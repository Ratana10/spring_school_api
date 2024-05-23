package com.piseth.schoolapi.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
