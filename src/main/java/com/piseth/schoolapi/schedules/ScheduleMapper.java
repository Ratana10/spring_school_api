package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {CourseService.class}
)
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "courseId", target = "course.id")
    Schedule toSchedule(ScheduleDTO enrollDTO);

    @Mapping(source = "course.id", target = "courseId")
    ScheduleDTO toScheduleDTO(Schedule enroll);

//    default List<Enroll> toScheduleList(List<ScheduleDTO> scheduleDTOS){
//        return sch.stream()
//                .map(this::toSchedule)
//                .collect(Collectors.toList());
//    }

}