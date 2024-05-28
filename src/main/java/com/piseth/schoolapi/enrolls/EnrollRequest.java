package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.payments.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class EnrollRequest {

    private Long studentId;

    private List<Long> courseIds;

    private LocalDateTime enrollDate;
    private BigDecimal amount;
    private PaymentType paymentType;
}
