package com.piseth.schoolapi.studytypes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "study_types")
public class StudyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
