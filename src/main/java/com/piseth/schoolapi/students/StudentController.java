package com.piseth.schoolapi.students;


import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody StudentDTO dto) {
        Student student = StudentMapper.INSTANCE.toStudent(dto);
        student = studentService.create(student);

        ApiResponse response = ApiResponse.builder()
                .data(StudentMapper.INSTANCE.toStudentDTO(student))
                .message("create student successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody StudentDTO dto) {
        Student student = StudentMapper.INSTANCE.toStudent(dto);
        student = studentService.update(id, student);

        ApiResponse response = ApiResponse.builder()
                .data(StudentMapper.INSTANCE.toStudentDTO(student))
                .message("update student successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        studentService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete student successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        Optional<Student> byId = studentService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(StudentMapper.INSTANCE.toStudentDTO(byId.get()))
                .message("get student successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<Student> categories = studentService.getStudents();

        ApiResponse response = ApiResponse.builder()
                .data(categories)
                .message("get categories successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
