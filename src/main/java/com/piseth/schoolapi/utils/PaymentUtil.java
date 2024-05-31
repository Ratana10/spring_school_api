package com.piseth.schoolapi.utils;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollService;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentUtil {


    public static BigDecimal makePayment(Payment payment, Enroll enroll) {
        BigDecimal amount = payment.getAmount();
        BigDecimal remain = enroll.getRemain();
        BigDecimal cashback = BigDecimal.valueOf(0);

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

        return cashback;
    }


}
