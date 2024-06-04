package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.courses.Course;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDTO {
    @NotNull(message = "day is required")
    private DayOfWeek day;

    @NotNull(message = "start time is required")
    private LocalTime startTime;

    @NotNull(message = "end time is required")
    private LocalTime endTime;

    @NotNull(message = "course id is required")
    private Long courseId;
}
