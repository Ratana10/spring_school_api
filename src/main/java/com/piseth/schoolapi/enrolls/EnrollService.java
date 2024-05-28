package com.piseth.schoolapi.enrolls;

import java.util.List;

public interface EnrollService {
    List<EnrollDTO> create(EnrollRequest enrollRequest);

    Enroll update(Long id, Enroll enroll);

    void delete(Long id);

    Enroll getById(Long id);

    List<Enroll> getEnrollByCourseId(Long courseId);
    List<Enroll> getEnrollByStudentId(Long studentId);


}
