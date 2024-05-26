package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.students.Student;
import com.piseth.schoolapi.students.StudentService;
import com.piseth.schoolapi.students.StudentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollRepository enrollRepository;

    @Override
    public Enroll create(Enroll enroll) {
        Long studentId = enroll.getStudent().getId();
        Long courseId = enroll.getCourse().getId();

        Boolean enrolledCourse = enrollRepository.existsByStudentIdAndCourseId(studentId, courseId);

        if (enrolledCourse) {
            throw new ApiException(String.format("StudentId=%d enrolled the courseId=%d", studentId, courseId), HttpStatus.BAD_REQUEST);
        }

        //set the enroll course price
        Optional<Course> course = courseService.getById(courseId);
        Optional<Student> student = studentService.getById(studentId);

        if (student.isPresent() && course.isPresent()) {
            setCoursePrice(enroll, student.get(), course.get());
        }

        enroll.setPaymentStatus(PaymentStatus.UNPAID);

        return enrollRepository.save(enroll);

    }

    @Override
    public List<Enroll> createMultiple(List<Enroll> enrollList) {

        return enrollList;
    }

    @Override
    public Enroll update(Long id, Enroll enroll) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Enroll> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Enroll> getEnrollByCourseId() {
        return null;
    }

    @Override
    public List<Enroll> getEnrollByStudentId() {
        return null;
    }

    private void setCoursePrice(Enroll enroll, Student student , Course course) {
        if (student.getStudentType() == StudentType.STUDY) {
            enroll.setPrice(course.getStudentPrice());

        } else if (student.getStudentType() == StudentType.WORK) {
            enroll.setPrice(course.getNormalPrice());
        }

    }
}
