package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.enrolls.EnrollDTO;
import com.piseth.schoolapi.enrolls.EnrollRequest;
import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody EnrollmentDTO enrollmentDTO) {

        List<EnrollmentDTO> enrollmentDTOS = enrollmentService.create(enrollmentDTO);

        ApiResponse response = ApiResponse.builder()
                .data(enrollmentDTOS)
                .message("create enrollment successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        enrollmentService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete enrollment successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
