package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.exception.ApiResponse;
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

        List<ScheduleDTO> scheduleDTOList = schedules.stream()
                .map(scheduleMapper::toScheduleDTO)
                .toList();

        ApiResponse response = ApiResponse.builder()
                .data(scheduleDTOList)
                .message("get schedules successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

}
