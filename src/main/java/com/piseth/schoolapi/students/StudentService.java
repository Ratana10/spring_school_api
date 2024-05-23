package com.piseth.schoolapi.students;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);

    Optional<Student> getById(Long id);

    List<Student> getStudents();

}
