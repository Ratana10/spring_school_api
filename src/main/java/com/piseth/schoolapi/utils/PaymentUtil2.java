package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.enrollment.Enrollment;
import com.piseth.schoolapi.enrollment.EnrollmentRepository;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentRepository;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.payments.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PaymentUtil2 {
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;

    public BigDecimal makePayment(
            Enrollment enrollment,
            BigDecimal amount,
            PaymentType paymentType,
            LocalDate date
    ) {

        BigDecimal cashback = BigDecimal.ZERO;
        BigDecimal remain = enrollment.getRemain();

        if (amount.compareTo(remain) < 0) {
            remain = remain.subtract(amount);

            enrollment.setPaymentStatus(PaymentStatus.PARTIAL);
            enrollment.setRemain(remain);
        } else if (amount.compareTo(remain) > 0) {
            cashback = amount.subtract(remain);

            enrollment.setPaymentStatus(PaymentStatus.PAID);
            enrollment.setRemain(BigDecimal.ZERO);
        } else {
            enrollment.setPaymentStatus(PaymentStatus.PAID);
            enrollment.setRemain(BigDecimal.ZERO);
        }

        Payment payment = Payment.builder()
                .amount(amount)
                .enrollment(enrollment)
                .paymentType(paymentType)
                .paymentDate(date)
                .build();

        paymentRepository.save(payment);
        //update enrollment on payment status and remain
        enrollmentRepository.save(enrollment);
        return cashback;
    }

}
