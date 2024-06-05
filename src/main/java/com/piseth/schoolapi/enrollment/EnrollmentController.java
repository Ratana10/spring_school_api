package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.exception.ApiResponse;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.utils.PageDTO;
import com.piseth.schoolapi.utils.ParamType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final EnrollmentMapper enrollmentMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {

        enrollmentDTO = enrollmentService.create(enrollmentDTO);

        ApiResponse response = ApiResponse.builder()
                .data(enrollmentDTO)
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

    @GetMapping
    public ResponseEntity<?> getAllEnrollments(@RequestParam Map<String, String > params) {


        Page<Enrollment> enrollments = enrollmentService.getAllEnrollments(params);


        return ResponseEntity
                .ok()
                .body(new PageDTO(enrollments));

    }

    @GetMapping("/test/{id}")
    public ResponseEntity<ApiResponse> testMethod(@PathVariable Long id) {
        enrollmentService.getCourseEnrollmentByStudentId(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("test enrollment successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
