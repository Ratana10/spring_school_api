package com.piseth.schoolapi.courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>,
        JpaSpecificationExecutor<Course> {
    List<Course> findByCategoryId(Long categoryId);
    List<Course> findByStudyTypeId(Long studyTypeId);

}
