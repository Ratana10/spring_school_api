package com.piseth.schoolapi.enrollment;

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
public class EnrollmentSpec implements Specification<Enrollment> {
    private final EnrollmentFilter enrollmentFilter;
    List<Predicate> predicateList = new ArrayList<>();

    @Override
    public Predicate toPredicate(@NotNull Root<Enrollment> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder cb) {
        if (enrollmentFilter.getId() != null) {
            Predicate id = root.get("id").in(enrollmentFilter.getId());
            predicateList.add(id);
        }
        System.out.println(root);
        if (enrollmentFilter.getPaymentStatus() != null) {
            Predicate paymentStatus = cb.equal(root.get("paymentStatus"), enrollmentFilter.getPaymentStatus());
            predicateList.add(paymentStatus);
        }
        return cb.and(predicateList.toArray(Predicate[]::new));
    }
}
