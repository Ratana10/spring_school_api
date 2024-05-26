package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.students.Student;
import com.piseth.schoolapi.students.StudentService;
import com.piseth.schoolapi.students.StudentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Optional<Course> course = courseService.getById(enroll.getCourse().getId());
        Optional<Student> student = studentService.getById(enroll.getStudent().getId());

        //set the enroll course price
        if (student.isPresent() && course.isPresent()) {
            if (student.get().getStudentType() == StudentType.STUDY) {
                enroll.setPrice(course.get().getStudentPrice());

            } else if (student.get().getStudentType() == StudentType.WORK) {
                enroll.setPrice(course.get().getNormalPrice());

            }

            enroll.setPaymentStatus(PaymentStatus.UNPAID);
            enroll.setEnrollDate(LocalDateTime.now());
        }


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

    private void setCoursePrice(Enroll enroll){
        //get student and course info
        Optional<Student> student = studentService.getById(enroll.getStudent().getId());
        Optional<Course> course = courseService.getById(enroll.getCourse().getId());


        //set the enroll course price
        if (student.isPresent() && course.isPresent()) {
            if (student.get().getStudentType() == StudentType.STUDY) {
                enroll.setPrice(course.get().getStudentPrice());

            } else if (student.get().getStudentType() == StudentType.WORK) {
                enroll.setPrice(course.get().getNormalPrice());

            }

            enroll.setPaymentStatus(PaymentStatus.UNPAID);

        }

    }
}
