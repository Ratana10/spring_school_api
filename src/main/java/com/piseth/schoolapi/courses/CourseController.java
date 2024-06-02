package com.piseth.schoolapi.courses;


import com.piseth.schoolapi.exception.ApiResponse;
import com.piseth.schoolapi.schedules.Schedule;
import com.piseth.schoolapi.schedules.ScheduleDTO;
import com.piseth.schoolapi.schedules.ScheduleMapper;
import com.piseth.schoolapi.schedules.ScheduleService;
import com.piseth.schoolapi.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
public class CourseController {

    private final CourseService courseService;

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    @PreAuthorize("hasAuthority('course:write')")
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

    @PreAuthorize("hasAuthority('course:write')")
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
    @PreAuthorize("hasAuthority('course:write')")
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
    @PreAuthorize("hasAuthority('course:read')")
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
    @PreAuthorize("hasAuthority('course:read')")
    @GetMapping
    public ResponseEntity<PageDTO> getAll(@RequestParam Map<String , String > params) {
        Page<Course> courses = courseService.getCourses(params);

        return ResponseEntity
                .ok()
                .body(new PageDTO(courses));
    }

    @PreAuthorize("hasAuthority('course:write')")
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
