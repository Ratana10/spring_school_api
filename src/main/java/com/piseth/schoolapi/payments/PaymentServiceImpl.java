package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.enrolls.EnrollRepository;
import com.piseth.schoolapi.enrolls.EnrollService;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final EnrollService enrollService;

    private final PaymentRepository paymentRepository;
    @Override
    public Payment create(Payment payment) {

        Optional<Enroll> enrollById = enrollService.getById(payment.getEnroll().getId());
        if(enrollById.isEmpty()){
            throw new ResourceNotFoundException("Enroll", payment.getEnroll().getId());
        }

        AtomicReference<BigDecimal> cashBack = new AtomicReference<>(BigDecimal.valueOf(0));

        enrollById.ifPresent(enroll -> {
            if(payment.getAmount().compareTo(enroll.getRemain()) == 0){
                enroll.setPaymentStatus(PaymentStatus.PAID);
                enroll.setRemain(payment.getAmount().subtract(enroll.getRemain()));

            }
            else if(payment.getAmount().compareTo(enroll.getRemain()) < 0){
                enroll.setPaymentStatus(PaymentStatus.PARTIAL);
                enroll.setRemain(enroll.getRemain().subtract(payment.getAmount()));

            }else{
                enroll.setPaymentStatus(PaymentStatus.PAID);
                enroll.setRemain(BigDecimal.valueOf(0));

                cashBack.set(payment.getAmount().subtract(enroll.getRemain()));
            }

            enrollService.update(enroll.getId(), enroll);
        });
        return  paymentRepository.save(payment);
    }
}
