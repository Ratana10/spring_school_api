package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrolls.Enroll;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Column(name = "pay_amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enr_id")
    private Enroll enroll;

    @Column(name = "pay_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "pay_date")
    private LocalDate paymentDate;
}
