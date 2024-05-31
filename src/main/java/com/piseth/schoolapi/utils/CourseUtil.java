package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.students.StudentType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class CourseUtil {
    public BigDecimal checkCoursePrice(StudentType studyType, Course course) {
        if (studyType == StudentType.STUDY) {
            return course.getStudentPrice();
        }

        return course.getNormalPrice();
    }
}
