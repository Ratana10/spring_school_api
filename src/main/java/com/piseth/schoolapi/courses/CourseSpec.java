package com.piseth.schoolapi.courses;

import com.piseth.schoolapi.schedules.ScheduleMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseSpec implements Specification<Course> {
    private final CourseFilter courseFilter;
    List<Predicate> predicateList = new ArrayList<>();
    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(courseFilter.getId() != null){
            //simple query
            Predicate id = root.get("id").in(courseFilter.getId());
            predicateList.add(id);
        }
        if(courseFilter.getName() != null){
            //simple query
            Predicate name = cb.like(cb.upper(root.get("name")), "%" + courseFilter.getName().toUpperCase() + "%");

            predicateList.add(name);
        }
        return cb.and(predicateList.toArray(Predicate[]::new));
    }
}
