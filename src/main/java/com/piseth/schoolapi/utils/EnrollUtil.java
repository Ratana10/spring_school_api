package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.enrolls.EnrollRepository;
import com.piseth.schoolapi.students.StudentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class EnrollUtil {
    private final EnrollRepository enrollRepository;

    public String checkStudentEnrollTheCourse(Long studentId, Long courseId) {
        //check student enrolled
        Boolean temp = enrollRepository.existsByStudentIdAndCourseId(studentId, courseId);

        return temp ?
                String.format("StudentId=%d enrolled the courseId=%d already", studentId, courseId)
                : null;
    }

    public BigDecimal checkCoursePrice(StudentType studyType, Course course) {
        if (studyType == StudentType.STUDY) {
            return course.getStudentPrice();
        }

        return course.getNormalPrice();
    }
}
