package com.piseth.schoolapi.students;

import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, Student student) {
        Optional<Student> byId = getById(id);
        return byId.map(stu ->{
            stu.setName(student.getName());
            stu.setEmail(student.getEmail());
            stu.setPassword(student.getPassword());
            stu.setStudentType(student.getStudentType());
            stu.setPhone(student.getPhone());
            stu.setGender(student.getGender());

            return studentRepository.save(stu);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        Optional<Student> byId = getById(id);
        if (byId.isPresent()) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Student> getById(Long id) {
        return Optional.ofNullable(studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id)));
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
}
