package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.students.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EnrollmentUtil {
    private final  EnrollmentRepository enrollmentRepository;

    public void isStudentEnrollmentTheCourses(Student student, Set<Long> courseIds){

        List<Enrollment> existingEnrollments = enrollmentRepository.findByStudentIdAndCourseIds(student.getId(), courseIds);

        if(!existingEnrollments.isEmpty()){
            String enrolledCourseIds = existingEnrollments.stream()
                    .flatMap(enr -> enr.getCourses().stream())
                    .filter(course -> courseIds.contains(course.getId()))
                    .map(course -> course.getId().toString())
                    .collect(Collectors.joining(", "));

            throw new ApiException(
                    "Student ID " + student.getId() + " is already enrolled in courses with IDs: " + enrolledCourseIds,
                    HttpStatus.BAD_REQUEST
            );
        }

    }
}
