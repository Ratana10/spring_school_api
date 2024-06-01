package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollDTO;
import com.piseth.schoolapi.enrolls.EnrollRequest;
import com.piseth.schoolapi.promotion.Promotion;
import com.piseth.schoolapi.promotion.PromotionService;
import com.piseth.schoolapi.students.StudentService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {
                StudentService.class,
                CourseService.class,
        }
)
public interface EnrollmentMapper {
    @Mappings({
            @Mapping(source = "studentId", target = "student"),
            @Mapping(source = "courseIds", target = "courses")
    })
    Enrollment toEnrollment(EnrollmentDTO enrollmentDTO);
}
