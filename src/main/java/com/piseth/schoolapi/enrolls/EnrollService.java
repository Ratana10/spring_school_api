package com.piseth.schoolapi.enrolls;

import java.util.List;
import java.util.Optional;

public interface EnrollService {
    Enroll create(Enroll enroll);

    Enroll update(Long id, Enroll enroll);

    void delete(Long id);

    Optional<Enroll> getById(Long id);

    List<Enroll> getEnrollByCourseId();
    List<Enroll> getEnrollByStudentId();

}
