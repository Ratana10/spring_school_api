package com.piseth.schoolapi.students;

import com.piseth.schoolapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Student stu = getById(id);

        stu.setName(student.getName());
        stu.setEmail(student.getEmail());
        stu.setPassword(student.getPassword());
        stu.setStudentType(student.getStudentType());
        stu.setPhone(student.getPhone());
        stu.setGender(student.getGender());
        return studentRepository.save(stu);
    }

    @Override
    public void delete(Long id) {
        Student byId = getById(id);
        studentRepository.deleteById(id);
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
}
