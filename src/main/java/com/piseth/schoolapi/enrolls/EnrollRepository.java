package com.piseth.schoolapi.enrolls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
