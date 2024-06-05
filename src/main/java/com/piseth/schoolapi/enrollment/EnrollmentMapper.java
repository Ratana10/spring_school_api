package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.students.StudentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {
                StudentService.class,
                CourseService.class,
                EnrollmentService.class
        }
)
public interface EnrollmentMapper {
    @Mappings({
            @Mapping(source = "studentId", target = "student"),
            @Mapping(source = "courseIds", target = "courses")
    })
    Enrollment toEnrollment(EnrollmentDTO enrollmentDTO);

    @Mappings({
            @Mapping(source = "student.id", target = "studentId"),
            @Mapping(source = "courses", target = "courseIds", qualifiedByName = "courseSetToCourseIdSet")
    })
    EnrollmentDTO toEnrollmentDTO(Enrollment enrollment);


    @Mapping(source = "student.id", target = "studentId")
    EnrollmentResponse toEnrollmentResponse(Enrollment enrollment);

    @Named("courseSetToCourseIdSet")
    default Set<Long> courseSetToCourseIdSet(Set<Course> courses) {
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }
}
