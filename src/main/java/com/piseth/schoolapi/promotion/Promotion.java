package com.piseth.schoolapi.promotion;

import com.piseth.schoolapi.courses.Course;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;

    @Column(name = "pro_name")
    private String name;

    @Column(name = "pro_description")
    private String description;

    @Column(name = "pro_start_date")
    private LocalDateTime startDate;

    @Column(name = "pro_end_date")
    private LocalDateTime endDate;

    @ManyToMany
    @JoinTable(
            name = "promotion_courses",
            joinColumns = @JoinColumn(name = "pro_id"),
            inverseJoinColumns = @JoinColumn(name = "cou_id")
    )
    private Set<Course> courses = new HashSet<>();
}