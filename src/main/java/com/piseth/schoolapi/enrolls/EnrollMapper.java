package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.students.StudentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper(
//        componentModel = "spring",
//        uses = {StudentService.class, CourseService.class}
//)
public interface EnrollMapper {
    EnrollMapper INSTANCE = Mappers.getMapper(EnrollMapper.class);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "courseId", target = "course.id")
    Enroll toEnroll(EnrollDTO enrollDTO);
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollDTO toEnrollDTO(Enroll enroll);
}
