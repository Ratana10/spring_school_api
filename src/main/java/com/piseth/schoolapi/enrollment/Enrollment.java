package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.auditing.AuditingEntity;
import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.promotion.Promotion;
import com.piseth.schoolapi.students.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "enrollments")
public class Enrollment extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enr_id")
    private Long id;

    @Column(name = "enr_amount")
    private BigDecimal amount;

    @Column(name = "enr_remain")
    private BigDecimal remain;

    @Column(name = "enr_payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "stu_id")
    private Student student;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "enroll_course",
            joinColumns = @JoinColumn(name = "enr_id"),
            inverseJoinColumns = @JoinColumn(name = "cou_id")
    )
    private Set<Course> courses = new HashSet<>();


    @Column(name = "enr_date")
    private LocalDateTime enrollDate;

}
