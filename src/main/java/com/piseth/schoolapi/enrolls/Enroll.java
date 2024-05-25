package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.students.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "enr_payment_status")
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "enr_date")
    private LocalDateTime enrollDate;
}
