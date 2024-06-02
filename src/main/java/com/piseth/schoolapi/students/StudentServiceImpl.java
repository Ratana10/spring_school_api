package com.piseth.schoolapi.students;

import com.piseth.schoolapi.courses.CourseSpec;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.utils.PageUtil;
import com.piseth.schoolapi.utils.ParamType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public Page<Student> getStudents(Map<String, String> params) {
        StudentFilter studentFilter = new StudentFilter();

        if (params.containsKey(ParamType.ID.getName())){
            studentFilter.setId(Long.valueOf(params.get(ParamType.ID.getName())));
        }
        if(params.containsKey(ParamType.NAME.getName())){
            studentFilter.setName(params.get(ParamType.NAME.getName()));
        }
        if(params.containsKey(ParamType.GMAIL.getName())){
            studentFilter.setGmail(params.get(ParamType.GMAIL.getName()));
        }

        Pageable pageable = PageUtil.getPageable(params);
        StudentSpec studentSpec = new StudentSpec(studentFilter);
        return studentRepository.findAll(studentSpec, pageable);
    }
}
