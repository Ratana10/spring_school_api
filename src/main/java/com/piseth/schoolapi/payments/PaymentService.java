package com.piseth.schoolapi.payments;

import java.util.List;

public interface PaymentService {
    Payment create(Payment payment);
    List<Payment> findByEnrollmentId(Long enrollmentId);
    Payment getById(Long paymentId);
    void delete(Long paymentId);

}
