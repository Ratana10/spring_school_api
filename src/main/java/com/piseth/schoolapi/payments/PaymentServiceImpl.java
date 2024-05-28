package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final EnrollService enrollService;

    private final PaymentRepository paymentRepository;

    @Override
    public Payment create(Payment payment) {

        Enroll enroll = enrollService.getById(payment.getEnroll().getId());

        BigDecimal cashBack = BigDecimal.valueOf(0);

        if (payment.getAmount().compareTo(enroll.getRemain()) == 0) {
            enroll.setPaymentStatus(PaymentStatus.PAID);
            enroll.setRemain(payment.getAmount().subtract(enroll.getRemain()));

        } else if (payment.getAmount().compareTo(enroll.getRemain()) < 0) {
            enroll.setPaymentStatus(PaymentStatus.PARTIAL);
            enroll.setRemain(enroll.getRemain().subtract(payment.getAmount()));

        } else {
            enroll.setPaymentStatus(PaymentStatus.PAID);
            enroll.setRemain(BigDecimal.valueOf(0));

            cashBack = payment.getAmount().subtract(enroll.getRemain());
        }

        enrollService.update(enroll.getId(), enroll);

        return paymentRepository.save(payment);
    }
}
