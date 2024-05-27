package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.courses.Course;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDTO {

    private DayOfWeek day;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long courseId;
}
