package com.piseth.schoolapi.students;


import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseMapper;
import com.piseth.schoolapi.courses.CourseResponse;
import com.piseth.schoolapi.enrollment.EnrollmentService;
import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollDTO;
import com.piseth.schoolapi.enrolls.EnrollMapper;
import com.piseth.schoolapi.enrolls.EnrollService;
import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final EnrollService enrollService;
    private final EnrollmentService enrollmentService;
    private final EnrollMapper  enrollMapper;
    private final CourseMapper courseMapper;

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
        Student byId = studentService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(StudentMapper.INSTANCE.toStudentDTO(byId))
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

    @GetMapping("{id}/enrolls")
    public ResponseEntity<?> getStudentEnrollCourses(@PathVariable Long id) {
        List<Enroll> enrollByStudentId = enrollService.getEnrollByStudentId(id);
        System.out.println(enrollByStudentId);

        List<EnrollDTO> enrollDTOList  = enrollByStudentId.stream()
                .map(enrollMapper::toEnrollDTO)
                .toList();

        ApiResponse response = ApiResponse.builder()
                .data(enrollDTOList)
                .message("get enrolled by student id successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{studentId}/enrollments/courses")
    public ResponseEntity<?> getStudentEnrollmentCourses(@PathVariable Long studentId) {
        List<Course> enrollmentCourses = enrollmentService.getCourseEnrollmentByStudentId(studentId);

        List<CourseResponse> list = enrollmentCourses.stream()
                .map(courseMapper::toCourseResponse)
                .toList();
        ApiResponse response = ApiResponse.builder()
                .data(list)
                .message("get enrollment courses by student id successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
