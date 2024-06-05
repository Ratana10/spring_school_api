package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.payments.PaymentStatus;
import lombok.Data;

@Data
public class EnrollmentFilter {
    private Long id;
    private PaymentStatus paymentStatus;
}
