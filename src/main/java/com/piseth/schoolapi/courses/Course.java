package com.piseth.schoolapi.courses;

import com.piseth.schoolapi.categories.Category;
import com.piseth.schoolapi.studytypes.StudyType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cau_id")
    private Long id;

    @Column(name = "cou_name")
    private String name;

    @Column(name = "cou_description")
    private String description;

    @Column(name = "cou_image")
    private String image;

    @Column(name = "stu_price")
    private BigDecimal StudentPrice;

    @Column(name = "normal_price")
    private BigDecimal normalPrice;

    @ManyToOne
    @JoinColumn(name = "study_type_id")
    private StudyType studyType;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

}
