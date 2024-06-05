package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.payments.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@Builder
public class EnrollmentDTO {
    @NotNull(message = "student id is required")
    private Long studentId;
    @NotNull(message = "course ids is required")
    private Set<Long> courseIds;
    @NotNull(message = "enroll date id required")
    private LocalDateTime enrollDate;
    private BigDecimal amount;
    private PaymentType paymentType;
    private Long promotionId;
}
