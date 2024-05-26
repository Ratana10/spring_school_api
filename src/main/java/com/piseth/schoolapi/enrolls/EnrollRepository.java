package com.piseth.schoolapi.enrolls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enroll> findByCourseId(Long courseId);

    List<Enroll> findByStudentId(Long studentId);
}