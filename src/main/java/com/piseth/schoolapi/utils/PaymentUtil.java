package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.enrollment.Enrollment;
import com.piseth.schoolapi.enrollment.EnrollmentRepository;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentRepository;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.payments.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PaymentUtil {
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;

    public BigDecimal makePayment(
            Enrollment enrollment,
            BigDecimal amount,
            PaymentType paymentType,
            LocalDate date
    ) {

        BigDecimal cashback = checkPaymentStatus(enrollment, amount);

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

    public BigDecimal checkPaymentStatus(Enrollment enrollment, BigDecimal paidAmount) {
        BigDecimal cashback = BigDecimal.ZERO;
        BigDecimal remain = checkRemain(enrollment.getRemain());

        if (paidAmount.compareTo(remain) < 0) {
            remain = remain.subtract(paidAmount);

            enrollment.setPaymentStatus(PaymentStatus.PARTIAL);
            enrollment.setRemain(remain);
        } else if (paidAmount.compareTo(remain) > 0) {
            cashback = paidAmount.subtract(remain);

            enrollment.setPaymentStatus(PaymentStatus.PAID);
            enrollment.setRemain(BigDecimal.ZERO);
        } else {
            enrollment.setPaymentStatus(PaymentStatus.PAID);
            enrollment.setRemain(BigDecimal.ZERO);
        }
        return cashback;
    }

    public BigDecimal checkRemain(BigDecimal remain){
        if(remain.compareTo(BigDecimal.ZERO) == 0){
            throw new ApiException("Cannot make payment because remain = 0", HttpStatus.BAD_REQUEST);
        }

        return remain;
    }


}
