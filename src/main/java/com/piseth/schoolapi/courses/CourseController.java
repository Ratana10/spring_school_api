package com.piseth.schoolapi.courses;


import com.piseth.schoolapi.exception.ApiResponse;
import com.piseth.schoolapi.schedules.Schedule;
import com.piseth.schoolapi.schedules.ScheduleDTO;
import com.piseth.schoolapi.schedules.ScheduleMapper;
import com.piseth.schoolapi.schedules.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

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
        Course byId = courseService.getById(id);


        ApiResponse response = ApiResponse.builder()
                .data(CourseMapper.INSTANCE.toCourseDTO(byId))
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


    @GetMapping("{courseId}/schedules")
    public ResponseEntity<ApiResponse> getCourseSchedule(@PathVariable Long courseId) {
        List<Schedule> scheduleByCourseId = scheduleService.getScheduleByCourseId(courseId);


        List<ScheduleDTO> scheduleDTOList = scheduleByCourseId.stream()
                .map(scheduleMapper::toScheduleDTO)
                .toList();

        ApiResponse response = ApiResponse.builder()
                .data(scheduleDTOList)
                .message("get course schedule successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
