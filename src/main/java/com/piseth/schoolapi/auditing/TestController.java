package com.piseth.schoolapi.auditing;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.categories.CategoryDTO;
import com.piseth.schoolapi.categories.CategoryMapper;
import com.piseth.schoolapi.categories.CategoryService;
import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestServiceImpl testService ;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Test test) {

        Test test1 = testService.create(test);
        ApiResponse response = ApiResponse.builder()
                .data(test1)
                .message("create test successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody Test test) {

        Test test1 = testService.update(test);
        ApiResponse response = ApiResponse.builder()
                .data(test1)
                .message("update test successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
