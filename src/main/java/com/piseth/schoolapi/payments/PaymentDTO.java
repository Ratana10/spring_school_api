package com.piseth.schoolapi.payments;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {
    private Long enrollId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDate paymentDate;
}
