package com.piseth.schoolapi.courses;


import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.students.StudentServiceImpl;
import com.piseth.schoolapi.utils.PageUtil;
import com.piseth.schoolapi.utils.PaginationUtil;
import com.piseth.schoolapi.utils.ParamType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Course byId = getById(id);


        byId.setName(course.getName());
        byId.setDescription(course.getDescription());
        byId.setImage(course.getImage());
        byId.setStudentPrice(course.getStudentPrice());
        byId.setNormalPrice(course.getNormalPrice());
        byId.setStudyType(course.getStudyType());
        byId.setCategory(course.getCategory());
        return courseRepository.save(byId);

    }

    @Override
    public void delete(Long id) {
        Course byId = getById(id);

        courseRepository.deleteById(id);

    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
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

    @Override
    public  Page<Course> getCourses(Map<String, String> params) {
        CourseFilter courseFilter = new CourseFilter();

        if(params.containsKey(ParamType.ID.getName())){
            String id = params.get(ParamType.ID.getName());
            courseFilter.setId(Long.parseLong(id));
        }
        if(params.containsKey(ParamType.NAME.getName())){
            String name = params.get(ParamType.NAME.getName());
            courseFilter.setName(name);
        }

        Pageable pageable = PaginationUtil.getPageNumberAndPageSize(params);

        CourseSpec courseSpec = new CourseSpec(courseFilter);

        return courseRepository.findAll(courseSpec, pageable);
    }

    @Override
    public Set<Long> courseSetToCourseIdSet(Set<Course> courses) {
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }

}
