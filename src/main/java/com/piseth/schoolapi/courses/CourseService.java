package com.piseth.schoolapi.courses;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CourseService {
    Course create(Course course);

    Course update(Long id, Course course);

    void delete(Long id);

    Course getById(Long id);

    List<Course> getCourses();

    List<Course> getByCategoryId(Long categoryId);

    List<Course> getCourseByStudyTypeId(Long studyTypeId);
    Set<Course> findCoursesByIds(Set<Long> courseIds);
    Page<Course> getCourses(Map<String ,String > params);


}
