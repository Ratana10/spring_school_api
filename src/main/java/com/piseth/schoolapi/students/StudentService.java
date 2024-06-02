package com.piseth.schoolapi.students;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentService {
    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);

    Student getById(Long id);

    List<Student> getStudents();
    Page<Student> getStudents(Map<String,String> params);

}
