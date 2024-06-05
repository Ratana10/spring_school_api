package com.piseth.schoolapi.students;

import com.piseth.schoolapi.courses.CourseSpec;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.utils.PageUtil;
import com.piseth.schoolapi.utils.PaginationUtil;
import com.piseth.schoolapi.utils.ParamType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        if(checkExistsEmail(student.getEmail())){
            throw new ApiException("email already exists", HttpStatus.BAD_REQUEST);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, Student student) {
        Student stu = getById(id);
        if(checkExistsEmailAndIdNot(id, student.getEmail())){
            throw new ApiException("email already exists", HttpStatus.BAD_REQUEST);
        }
        stu.setName(student.getName());
        stu.setEmail(student.getEmail());
        stu.setStudentType(student.getStudentType());
        stu.setGender(student.getGender());
        return studentRepository.save(stu);
    }

    private boolean checkExistsEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    private boolean checkExistsEmailAndIdNot(Long id, String email) {
        return studentRepository.existsByEmailAndIdNot(email, id);
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

        Pageable pageable = PaginationUtil.getPageNumberAndPageSize(params);

        StudentSpec studentSpec = new StudentSpec(studentFilter);
        return studentRepository.findAll(studentSpec, pageable);
    }


}
