package com.piseth.schoolapi.students;

import jakarta.persistence.*;
import lombok.Data;

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
    private String type;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "gender")
    private String gender;
}
