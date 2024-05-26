package com.piseth.schoolapi.students;

import com.piseth.schoolapi.enrolls.Enroll;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private Long id;

    @Column(name = "stu_name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "stu_type")
    @Enumerated(EnumType.STRING)
    private StudentType studentType;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "gender")
    private String gender;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enroll> enrolls = new ArrayList<>();
}
