package com.piseth.schoolapi.enrolls;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EnrollService {
    Enroll create(Enroll enroll);
    List<Enroll> createMultiple(List<Enroll> enrollList);

    Enroll update(Long id, Enroll enroll);

    void delete(Long id);

    Enroll getById(Long id);

    List<Enroll> getEnrollByCourseId(Long courseId);
    List<Enroll> getEnrollByStudentId(Long studentId);


}
