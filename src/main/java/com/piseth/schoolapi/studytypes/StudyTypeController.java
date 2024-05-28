package com.piseth.schoolapi.studytypes;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/study-types")
@RequiredArgsConstructor
public class StudyTypeController {

    private final StudyTypeService studyTypeService;

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody StudyTypeDTO dto) {
        StudyType studyType = StudyTypeMapper.INSTANCE.toStudyType(dto);
        studyType = studyTypeService.create(studyType);

        ApiResponse response = ApiResponse.builder()
                .data(StudyTypeMapper.INSTANCE.toStudyTypeDTO(studyType))
                .message("create study-type successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody StudyTypeDTO dto) {
        StudyType studyType = StudyTypeMapper.INSTANCE.toStudyType(dto);
        studyType = studyTypeService.update(id, studyType);

        ApiResponse response = ApiResponse.builder()
                .data(StudyTypeMapper.INSTANCE.toStudyTypeDTO(studyType))
                .message("update study-type successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        studyTypeService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete study-type successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        StudyType byId = studyTypeService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(StudyTypeMapper.INSTANCE.toStudyTypeDTO(byId))
                .message("get study-type successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<StudyType> studyTypes = studyTypeService.getStudyTypes();

        ApiResponse response = ApiResponse.builder()
                .data(studyTypes)
                .message("get study-type successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{id}/courses")
    public ResponseEntity<ApiResponse> getCourseByStudyTypeId(@PathVariable Long id){
        List<Course> courseList = courseService.getCourseByStudyTypeId(id);

        ApiResponse response = ApiResponse.builder()
                .data(courseList)
                .message("get course by studyType id successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
