package com.piseth.schoolapi.courses;


import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course update(Long id, Course course) {
        Optional<Course> byId = getById(id);
        return byId.map(c ->{
            c.setName(course.getName());
            c.setDescription(course.getDescription());
            c.setImage(course.getImage());
            c.setStudentPrice(course.getStudentPrice());
            c.setNormalPrice(course.getNormalPrice());
//            c.setStudyType(course.getStudyType());
//            c.setCategory(course.getCategory());
            return courseRepository.save(c);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        Optional<Course> byId = getById(id);
        if (byId.isPresent()) {
            courseRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Course> getById(Long id) {
        return Optional.ofNullable(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id)));
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getByCategoryId(Long categoryId) {
        return courseRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Course> getCourseByStudyTypeId(Long studyTypeId) {
        return courseRepository.findByStudyTypeId(studyTypeId);
    }

    @Override
    public Set<Course> findCoursesByIds(Set<Long> courseIds) {
        return new HashSet<>(courseRepository.findAllById(courseIds));
    }

}
