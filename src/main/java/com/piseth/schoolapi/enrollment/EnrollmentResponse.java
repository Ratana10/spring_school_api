package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.payments.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class EnrollmentResponse {
    private Long studentId;
    private BigDecimal amount;
    private BigDecimal remain;
    private PaymentStatus paymentStatus;
    private LocalDateTime enrollDate;
}
