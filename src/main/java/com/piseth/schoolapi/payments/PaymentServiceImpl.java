package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrollment.Enrollment;
import com.piseth.schoolapi.enrollment.EnrollmentRepository;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.utils.PaymentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentUtil paymentUtil;

    @Override
    public Payment create(Payment payment) {
        Enrollment enrollment = payment.getEnrollment();

        BigDecimal cashback = paymentUtil.checkPaymentStatus(enrollment, payment.getAmount());

        enrollmentRepository.save(enrollment);

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
        Payment paymentById = getById(paymentId);
        Enrollment enrollment = paymentById.getEnrollment();

        BigDecimal remain = enrollment.getRemain().add(paymentById.getAmount());
        enrollment.setRemain(remain);

        if(remain.compareTo(enrollment.getAmount()) == 0){
            enrollment.setPaymentStatus(PaymentStatus.UNPAID);

        }else if (remain.compareTo(enrollment.getAmount()) < 0){
            enrollment.setPaymentStatus(PaymentStatus.PARTIAL);

        }else if (remain.compareTo(enrollment.getAmount()) > 0){
            enrollment.setPaymentStatus(PaymentStatus.PAID);

        }

        //update enrollment on remain and paymentStatus
        enrollmentRepository.save(enrollment);
        paymentRepository.deleteById(paymentId);
    }
}
