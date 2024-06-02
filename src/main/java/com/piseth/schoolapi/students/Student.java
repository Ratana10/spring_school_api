package com.piseth.schoolapi.students;

import com.piseth.schoolapi.auditing.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private Long id;

    @Column(name = "stu_name")
    private String name;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "stu_type")
    @Enumerated(EnumType.STRING)
    private StudentType studentType;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
