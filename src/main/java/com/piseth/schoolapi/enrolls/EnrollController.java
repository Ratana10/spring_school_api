package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/enrolls")
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollMapper enrollMapper;
    private final EnrollService enrollService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody EnrollDTO enrollDTO) {

        Enroll enroll = enrollMapper.toEnroll(enrollDTO);

        enroll = enrollService.create(enroll);

        ApiResponse response = ApiResponse.builder()
                .data(enroll)
                .message("create enroll successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse> createMultiple(@RequestBody EnrollsDTO enrollsDTO) {

        List<EnrollDTO> listEnrollDTO = enrollsDTO.getCourseIds().stream()
                .map(courseId ->EnrollDTO.builder()
                            .studentId(enrollsDTO.getStudentId())
                            .courseId(courseId)
                            .enrollDate(enrollsDTO.getEnrollDate())
                            .build()
                ).toList();

        //mapping dto to enroll
        List<Enroll> enrollList = enrollMapper.toEnrollList(listEnrollDTO);

        enrollList = enrollService.createMultiple(enrollList);

        ApiResponse response = ApiResponse.builder()
                .data(enrollList)
                .message("create multiple enroll successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
