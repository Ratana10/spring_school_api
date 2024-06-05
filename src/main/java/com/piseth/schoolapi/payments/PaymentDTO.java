package com.piseth.schoolapi.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {
    @NotNull(message = "enrollment id is required")
    private Long enrollmentId;
    @NotNull(message = "amount is required")
    private BigDecimal amount;
    @NotNull(message = "payment type is required")
    private PaymentType paymentType;
    @NotNull(message = "payment date is required")
    private LocalDate paymentDate;
}
