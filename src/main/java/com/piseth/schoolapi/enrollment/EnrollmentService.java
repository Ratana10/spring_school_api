package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollDTO;
import com.piseth.schoolapi.enrolls.EnrollRequest;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentDTO> create(EnrollmentDTO enrollmentDTO);

    void delete(Long id);
    Enrollment getById(Long id);
}
