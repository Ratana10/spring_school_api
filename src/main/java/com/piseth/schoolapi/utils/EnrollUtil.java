package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollRepository;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class EnrollUtil {
    private final EnrollRepository enrollRepository;

    public List<String> validateStudentEnrolledTheCourses(Long studentId, Set<Long> courseIds) {
        return courseIds.stream()
                .map(courseId -> checkStudentEnrollTheCourse(studentId, courseId))
                .filter(Objects::nonNull)
                .toList();
    }

    public String checkStudentEnrollTheCourse(Long studentId, Long courseId) {
        //check student enrolled
        Boolean temp = enrollRepository.existsByStudentIdAndCourseId(studentId, courseId);

        return temp ?
                String.format("StudentId=%d enrolled the courseId=%d already", studentId, courseId)
                : null;
    }

    public BigDecimal updateEnrollPaymentStatusAndRemain(Enroll enroll, Payment payment){
        BigDecimal amount = payment.getAmount();
        BigDecimal remain = enroll.getRemain();

        BigDecimal cashback = BigDecimal.ZERO;
        if (amount.compareTo(remain) == 0) {            //amount equal remain
            enroll.setPaymentStatus(PaymentStatus.PAID);
            enroll.setRemain(amount.subtract(remain));

        } else if (amount.compareTo(remain) < 0) {      //amount less than remain
            enroll.setPaymentStatus(PaymentStatus.PARTIAL);
            enroll.setRemain(remain.subtract(amount));

        }else{                                           //amount more than remain
            enroll.setPaymentStatus(PaymentStatus.PAID);
            enroll.setRemain(BigDecimal.valueOf(0));
            cashback = amount.subtract(remain);
        }

        enrollRepository.save(enroll);

        return cashback;
    }

//    public BigDecimal makePayment(Payment payment, Enroll enroll) {
//        BigDecimal amount = payment.getAmount();
//        BigDecimal remain = enroll.getRemain();
//        BigDecimal cashback = BigDecimal.valueOf(0);
//
//        if (amount.compareTo(remain) == 0) {            //amount equal remain
//            enroll.setPaymentStatus(PaymentStatus.PAID);
//            enroll.setRemain(amount.subtract(remain));
//
//        } else if (amount.compareTo(remain) < 0) {      //amount less than remain
//            enroll.setPaymentStatus(PaymentStatus.PARTIAL);
//            enroll.setRemain(remain.subtract(amount));
//
//        }else{                                           //amount more than remain
//            enroll.setPaymentStatus(PaymentStatus.PAID);
//            enroll.setRemain(BigDecimal.valueOf(0));
//            cashback = amount.subtract(remain);
//        }
//
//        enrollRepository.save(enroll);
//
//        return cashback;
//    }




}
