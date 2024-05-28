package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.students.StudentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {StudentService.class, CourseService.class}
)
public interface EnrollMapper {
    EnrollMapper INSTANCE = Mappers.getMapper(EnrollMapper.class);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "courseId", target = "course")
    Enroll toEnroll(EnrollDTO enrollDTO);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollDTO toEnrollDTO(Enroll enroll);

    default List<Enroll> toEnrollList(List<EnrollDTO> enrollDTOS){
        return enrollDTOS.stream()
                .map(this::toEnroll)
                .collect(Collectors.toList());
    }
}
