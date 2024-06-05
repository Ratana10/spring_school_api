package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ScheduleDTO dto) {

        Schedule schedule = scheduleMapper.toSchedule(dto);
        schedule = scheduleService.create(schedule);

        ApiResponse response = ApiResponse.builder()
                .data(scheduleMapper.toScheduleDTO(schedule))
                .message("create schedule successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id,@Valid @RequestBody ScheduleDTO dto) {

        Schedule schedule = scheduleMapper.toSchedule(dto);
        schedule = scheduleService.update(id, schedule);

        ApiResponse response = ApiResponse.builder()
                .data(scheduleMapper.toScheduleDTO(schedule))
                .message("update schedule successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        scheduleService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete schedule successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<Schedule> schedules = scheduleService.getAll();

        ApiResponse response = ApiResponse.builder()
                .data(schedules.stream().map(scheduleMapper::toScheduleDTO))
                .message("get schedules successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

}
