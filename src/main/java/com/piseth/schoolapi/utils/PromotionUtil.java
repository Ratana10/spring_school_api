package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollRequest;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.promotion.Promotion;
import com.piseth.schoolapi.students.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PromotionUtil {
    private final EnrollUtil enrollUtil;

    public void applyPromotion(EnrollRequest request, Student student, List<Enroll> enrolls, Promotion promotion) {

        boolean validDate = isEnrollDateInPromotionDate(request.getEnrollDate(), promotion.getStartDate(), promotion.getEndDate());
        boolean validCourse = isEnrollCourseMeetPromotionCourseRequirement(enrolls, promotion.getRequiredCourses());

        if (validDate && validCourse) {
            if (!promotion.getPromotionCourses().isEmpty()) {
                applyCoursePromotion(request, student, enrolls, promotion);
            } else if (promotion.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) {
                applyDiscountAmountPromotion(enrolls, promotion.getDiscountAmount());

            } else if (promotion.getDiscountPercentage().compareTo(BigDecimal.ZERO) != 0) {
                applyDiscountPercentPromotion(enrolls, promotion.getDiscountPercentage());

            }
        }
    }

    private void applyDiscountAmountPromotion(List<Enroll> enrolls, BigDecimal discountValue) {
        for (Enroll enroll : enrolls) {
            BigDecimal price = enroll.getPrice();

            price = calculateAmount(price, discountValue);
            enroll.setPrice(price);
            enroll.setRemain(price);
        }
    }

    private void applyDiscountPercentPromotion(List<Enroll> enrolls, BigDecimal discountPercentage) {
        for (Enroll enroll : enrolls) {
            BigDecimal price = enroll.getPrice();

            price = calculateDiscountPercent(price, discountPercentage);
            enroll.setPrice(price);
            enroll.setRemain(price);
        }
    }

    private void applyCoursePromotion(EnrollRequest request, Student student, List<Enroll> enrolls, Promotion promotion) {

        for (Course course : promotion.getPromotionCourses()) {
            String error = enrollUtil.checkStudentEnrollTheCourse(student.getId(), course.getId());

            if (error != null) {
                continue;
            }

            Enroll freeEnroll = Enroll.builder()
                    .student(student)
                    .course(course)
                    .price(BigDecimal.ZERO)
                    .remain(BigDecimal.ZERO)
                    .paymentStatus(PaymentStatus.PAID)
                    .enrollDate(request.getEnrollDate())
                    .build();

            enrolls.add(freeEnroll);

        }
    }

    private BigDecimal calculateAmount(BigDecimal price, BigDecimal discountValue) {
        return price.subtract(discountValue);
    }

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


    private boolean isEnrollCourseMeetPromotionCourseRequirement(List<Enroll> enrolls, Set<Course> promotionCourses) {
        Set<Course> courses = enrolls.stream().map(Enroll::getCourse).collect(Collectors.toSet());

        return courses.containsAll(promotionCourses)
                && courses.size() >= promotionCourses.size();
    }

}
