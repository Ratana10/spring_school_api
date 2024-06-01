package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.enrollment.Enrollment;
import com.piseth.schoolapi.enrollment.EnrollmentRepository;
import com.piseth.schoolapi.promotion.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PromotionUtil2 {

    private final EnrollmentRepository enrollmentRepository;

    public void applyPromotion(Enrollment enrollment, Promotion promotion) {

        boolean validDate = isEnrollDateInPromotionDate(
                enrollment.getEnrollDate(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );

        boolean validCourse = isEnrollCourseMeetPromotionCourseRequirement(
                enrollment.getCourses(),
                promotion.getRequiredCourses()
        );

        if (validDate && validCourse) {
            if (!promotion.getPromotionCourses().isEmpty()) {
                applyCoursePromotion(enrollment, promotion);

            } else if (promotion.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) {
                applyDiscountAmountPromotion(enrollment, promotion.getDiscountAmount());

            } else if (promotion.getDiscountPercentage().compareTo(BigDecimal.ZERO) != 0) {
                applyDiscountPercentPromotion(enrollment, promotion.getDiscountPercentage());
            }
        }

    }

    private void applyCoursePromotion(Enrollment enrollment, Promotion promotion) {
        for (Course proCourse : promotion.getPromotionCourses()) {

            //if student enroll the promotion course already skip this promotion
            Optional<Enrollment> byStudentIdAndCourseId =
                    enrollmentRepository.findByStudentIdAndCourseId(enrollment.getStudent().getId(), proCourse.getId());
            if (byStudentIdAndCourseId.isPresent()) continue;

            enrollment.getCourses().add(proCourse);
        }
    }

    private void applyDiscountAmountPromotion(Enrollment enrollment, BigDecimal discountValue) {
        enrollment.setAmount(discountValue);
        enrollment.setRemain(discountValue);
    }

    private void applyDiscountPercentPromotion(Enrollment enrollment, BigDecimal discountPercentage) {
        enrollment.setAmount(calculateDiscountPercent(enrollment.getAmount(), discountPercentage));
        enrollment.setRemain(calculateDiscountPercent(enrollment.getAmount(), discountPercentage));
    }

//    private BigDecimal calculateAmount(BigDecimal price, BigDecimal discountValue) {
//        return price.subtract(discountValue);
//    }

    private BigDecimal calculateDiscountPercent(BigDecimal price, BigDecimal discountValue) {
        return price.subtract(price.multiply(discountValue));
    }

    private boolean isEnrollDateInPromotionDate(LocalDateTime enrollDate, LocalDateTime proStartDate, LocalDateTime proEndDate) {

        if (enrollDate == null || proStartDate == null || proEndDate == null) {
            return false;
        }
        //startDate <= enrollDate <= endDate
        return (proStartDate.isBefore(enrollDate) || proStartDate.equals(enrollDate))
                && (proEndDate.isAfter(enrollDate) || proEndDate.equals(enrollDate));
    }


    private boolean isEnrollCourseMeetPromotionCourseRequirement(Set<Course> enrollmentCourse,
                                                                 Set<Course> promotionCourses) {

        return enrollmentCourse.containsAll(promotionCourses)
                && enrollmentCourse.size() >= promotionCourses.size();
    }
}
