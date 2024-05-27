package com.piseth.schoolapi.schedules;

import com.piseth.schoolapi.courses.Course;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "course_schedule")
@SQLDelete(sql = "UPDATE course_schedule SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Schedule {
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

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

}
