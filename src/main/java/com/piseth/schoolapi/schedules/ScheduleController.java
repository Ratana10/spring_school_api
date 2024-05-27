package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody ScheduleDTO dto) {

        Schedule schedule = ScheduleMapper.INSTANCE.toSchedule(dto);
        schedule = scheduleService.create(schedule);

        ApiResponse response = ApiResponse.builder()
                .data(ScheduleMapper.INSTANCE.toScheduleDTO(schedule))
                .message("create schedule successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody ScheduleDTO dto) {

        Schedule schedule = ScheduleMapper.INSTANCE.toSchedule(dto);
        schedule = scheduleService.update(id, schedule);

        ApiResponse response = ApiResponse.builder()
                .data(ScheduleMapper.INSTANCE.toScheduleDTO(schedule))
                .message("update schedule successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
