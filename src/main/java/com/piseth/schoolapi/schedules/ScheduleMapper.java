package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.courses.CourseService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {CourseService.class, ScheduleService.class}
)
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "courseId", target = "course")
    Schedule toSchedule(ScheduleDTO scheduleDTO);

    @Mapping(source = "course.id", target = "courseId")
    ScheduleDTO toScheduleDTO(Schedule schedule);

}
