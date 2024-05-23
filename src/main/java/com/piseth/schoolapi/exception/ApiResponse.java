package com.piseth.schoolapi.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private final Object data;
    private final String message;
    private final Integer httpStatus;

}
