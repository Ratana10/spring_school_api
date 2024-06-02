package com.piseth.schoolapi.students;

import com.piseth.schoolapi.utils.ParamType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentSpec implements Specification<Student> {
    private final StudentFilter StudentFilter;
    List<Predicate> predicateList = new ArrayList<>();

    @Override
    public Predicate toPredicate(@NotNull Root<Student> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder cb) {
        if (StudentFilter.getId() != null) {
            //simple query
            Predicate id = root.get(ParamType.ID.getName()).in(StudentFilter.getId());
            predicateList.add(id);
        }
        if (StudentFilter.getName() != null) {

            Predicate name = cb.like(cb.upper(root.get((ParamType.NAME.getName()))),
                    "%" + StudentFilter.getName().toUpperCase() + "%");

            predicateList.add(name);
        }
        if (StudentFilter.getGmail() != null) {

            Predicate gmail = cb.like(cb.upper(root.get((ParamType.GMAIL.getName()))),
                    "%" + StudentFilter.getName().toUpperCase() + "%");

            predicateList.add(gmail);
        }
        return cb.and(predicateList.toArray(Predicate[]::new));
    }
}
