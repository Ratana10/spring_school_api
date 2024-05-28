package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.students.Student;
import com.piseth.schoolapi.students.StudentService;
import com.piseth.schoolapi.students.StudentType;
import com.piseth.schoolapi.studytypes.StudyType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
//        Long studentId = enroll.getStudent().getId();
//        Long courseId = enroll.getCourse().getId();
//
//        String error = checkStudentEnrollTheCourse(studentId, courseId);
//
//        if (error != null) {
//            throw new ApiException(error, HttpStatus.BAD_REQUEST);
//        }
//
//        //set the enroll course price
//        Course course = courseService.getById(courseId);
//        Student student = studentService.getById(studentId);
//
//        setCoursePrice(enroll, student, course);
//
//
//        enroll.setRemain(enroll.getPrice());
//        enroll.setPaymentStatus(PaymentStatus.UNPAID);

//        return enrollRepository.save(enroll);
return null;
    }

//    @Override
//    public List<Enroll> createMultiple(List<Enroll> enrollList) {
//        Long studentId = enrollList.get(0).getStudent().getId();
//
//        List<String> errors = new ArrayList<>();
//        List<Enroll> savedEnroll = new ArrayList<>();
//
//        for (Enroll enroll : enrollList) {
//            Long courseId = enroll.getCourse().getId();
//
//            String error = checkStudentEnrollTheCourse(studentId, courseId);
//            if (error != null) {
//                errors.add(error);
//                continue;
//            }
//
//            Course course = courseService.getById(courseId);
//            Student student = studentService.getById(studentId);
//
//            setCoursePrice(enroll, student, course);
//
//            enroll.setRemain(enroll.getPrice());
//            enroll.setPaymentStatus(PaymentStatus.UNPAID);
//            //add to list for save later
//            savedEnroll.add(enroll);
//        }
//
//        if (errors.isEmpty()) {
//            return enrollRepository.saveAll(savedEnroll);
//        } else {
//            throw new ApiException(String.join(";", errors), HttpStatus.BAD_REQUEST);
//        }
//    }

    @Override
    public List<Enroll> createMultiple(List<Enroll> enrollList) {
        Student student = enrollList.get(0).getStudent();

        List<String> errors = new ArrayList<>();
        List<Enroll> savedEnroll = new ArrayList<>();

        for(Enroll enroll : enrollList){
            Course course = enroll.getCourse();

            String error = checkStudentEnrollTheCourse(student.getId(), course.getId());
            if (error != null) {
                errors.add(error);
                continue;
            }

            BigDecimal coursePrice = checkCoursePrice(student.getStudentType(), course);
            enroll.setPrice(coursePrice);
            enroll.setRemain(coursePrice);
            enroll.setPaymentStatus(PaymentStatus.UNPAID);

            savedEnroll.add(enroll);
        }

        if (!errors.isEmpty()) {
            throw new ApiException(String.join(";", errors), HttpStatus.BAD_REQUEST);

        }

        return enrollRepository.saveAll(savedEnroll);
    }
    @Override
    public Enroll update(Long id, Enroll enroll) {

        Enroll byId = getById(id);

        byId.setPrice(enroll.getPrice());
        byId.setRemain(enroll.getRemain());
        byId.setPaymentStatus(enroll.getPaymentStatus());
        byId.setStudent(enroll.getStudent());
        byId.setCourse(enroll.getCourse());
        byId.setEnrollDate(enroll.getEnrollDate());

        return enrollRepository.save(byId);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Enroll getById(Long id) {
        return enrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enroll", id));
    }

    @Override
    public List<Enroll> getEnrollByCourseId(Long courseId) {
        courseService.getById(courseId);
        return enrollRepository.findByCourseId(courseId);
    }

    @Override
    public List<Enroll> getEnrollByStudentId(Long studentId) {
        studentService.getById(studentId);
        return enrollRepository.findByStudentId(studentId);
    }

    private void setCoursePrice1(Enroll enroll, Student student, Course course) {
        if (student.getStudentType() == StudentType.STUDY) {
            enroll.setPrice(course.getStudentPrice());

        } else if (student.getStudentType() == StudentType.WORK) {
            enroll.setPrice(course.getNormalPrice());
        }
    }

    private BigDecimal checkCoursePrice(StudentType studyType, Course course ){
       if(studyType == StudentType.STUDY){
           return course.getStudentPrice();
       }

       return course.getNormalPrice();
    }


    private String checkStudentEnrollTheCourse(Long studentId, Long courseId) {
        //check student enrolled
        Boolean temp = enrollRepository.existsByStudentIdAndCourseId(studentId, courseId);

        if (temp) {
            return String.format("StudentId=%d enrolled the courseId=%d already", studentId, courseId);
        } else {
            return null;
        }

    }
}
