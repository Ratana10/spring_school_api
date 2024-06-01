package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollService;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final EnrollService enrollService;

    private final PaymentRepository paymentRepository;

    @Override
    public Payment create(Payment payment) {

        Enroll enroll = payment.getEnroll();

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

    @Override
    public List<Payment> findByEnrollmentId(Long enrollmentId) {
        return paymentRepository.findByEnrollmentId(enrollmentId);
    }

    @Override
    public Payment getById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));
    }

    @Override
    public void delete(Long paymentId) {
        getById(paymentId);
        paymentRepository.deleteById(paymentId);
    }
}
