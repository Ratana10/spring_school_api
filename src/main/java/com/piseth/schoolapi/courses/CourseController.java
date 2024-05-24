package com.piseth.schoolapi.courses;


import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CourseDTO dto) {
        Course course = CourseMapper.INSTANCE.toCourse(dto);
        course = courseService.create(course);

        ApiResponse response = ApiResponse.builder()
                .data(CourseMapper.INSTANCE.toCourseDTO(course))
                .message("create course successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody CourseDTO dto) {
        Course course = CourseMapper.INSTANCE.toCourse(dto);
        course = courseService.update(id, course);

        ApiResponse response = ApiResponse.builder()
                .data(CourseMapper.INSTANCE.toCourseDTO(course))
                .message("update course successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        courseService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete course successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        Optional<Course> byId = courseService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(CourseMapper.INSTANCE.toCourseDTO(byId.get()))
                .message("get course successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<Course> categories = courseService.getCourses();

        ApiResponse response = ApiResponse.builder()
                .data(categories)
                .message("get courses successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
