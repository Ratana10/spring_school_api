package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.students.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e JOIN e.courses c WHERE e.student.id = :studentId AND c.id IN :courseIds")
    List<Enrollment> findByStudentIdAndCourseIds(Long studentId, Set<Long> courseIds);

    @Query("SELECT e FROM Enrollment e JOIN e.courses c WHERE e.student.id = :studentId AND c.id = :courseId")
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
}