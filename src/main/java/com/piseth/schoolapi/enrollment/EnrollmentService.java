package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.courses.Course;

import java.util.List;
import java.util.Set;

public interface EnrollmentService {
    EnrollmentDTO create(EnrollmentDTO enrollmentDTO);

    void delete(Long id);
    Enrollment getById(Long id);

    Enrollment findStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findStudentIdAndCourseIds(Long studentId, Set<Long> courseIds);
    List<Course> getCourseEnrollmentByStudentId(Long studentId);
}
