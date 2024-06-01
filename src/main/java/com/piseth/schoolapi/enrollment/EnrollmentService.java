package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollDTO;
import com.piseth.schoolapi.enrolls.EnrollRequest;
import com.piseth.schoolapi.payments.PaymentStatus;

import java.util.List;
import java.util.Set;

public interface EnrollmentService {
    List<EnrollmentDTO> create(EnrollmentDTO enrollmentDTO);

    void delete(Long id);
    Enrollment getById(Long id);

    Enrollment findStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findStudentIdAndCourseIds(Long studentId, Set<Long> courseIds);
}
