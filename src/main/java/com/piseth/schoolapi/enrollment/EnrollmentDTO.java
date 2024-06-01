package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.payments.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@Builder
public class EnrollmentDTO {
    private Long studentId;
    private Set<Long> courseIds;
    private LocalDateTime enrollDate;
    private BigDecimal amount;
    private PaymentType paymentType;
    private Long promotionId;
}
