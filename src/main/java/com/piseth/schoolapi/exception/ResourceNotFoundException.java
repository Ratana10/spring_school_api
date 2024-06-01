package com.piseth.schoolapi.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{
    public ResourceNotFoundException(String resource, Long id){
        super(String.format("%s with id=%d not found!", resource, id), HttpStatus.NOT_FOUND);
    }
}
