package com.piseth.schoolapi.courses;


import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.utils.PageUtil;
import com.piseth.schoolapi.utils.ParamType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        Course byId = getById(id);


        byId.setName(course.getName());
        byId.setDescription(course.getDescription());
        byId.setImage(course.getImage());
        byId.setStudentPrice(course.getStudentPrice());
        byId.setNormalPrice(course.getNormalPrice());
//            c.setStudyType(course.getStudyType());
//            c.setCategory(course.getCategory());
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

        int page = 1;
        if((params.containsKey(PageUtil.PAGE_NUMBER))){
            page = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int size = 1;
        if((params.containsKey(PageUtil.PAGE_SIZE))){
            size = Integer.parseInt(params.get(PageUtil.PAGE_SIZE));
        }

        Pageable pageable = PageUtil.getPageable(page, size);
        CourseSpec courseSpec = new CourseSpec(courseFilter);

        return courseRepository.findAll(courseSpec, pageable);
    }

}
