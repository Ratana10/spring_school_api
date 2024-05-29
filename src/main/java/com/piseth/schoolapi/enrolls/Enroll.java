package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.students.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "enrolls")
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enr_id")
    private Long id;

    @Column(name = "enr_course_price")
    private BigDecimal price;

    @Column(name = "enr_remain")
    private BigDecimal remain;

    @Column(name = "enr_payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "stu_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cou_id")
    private Course course;


    @Column(name = "enr_date")
    private LocalDateTime enrollDate;
}
