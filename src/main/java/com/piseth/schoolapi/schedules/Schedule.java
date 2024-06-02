package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.auditing.AuditingEntity;
import com.piseth.schoolapi.courses.Course;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "course_schedule")
public class Schedule extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private DayOfWeek day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
