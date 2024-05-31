package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrolls")
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollService enrollService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody EnrollRequest enrollRequest) {

        List<EnrollDTO> enrollDTOList = enrollService.create(enrollRequest);

        ApiResponse response = ApiResponse.builder()
                .data(enrollDTOList)
                .message("create enroll successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
