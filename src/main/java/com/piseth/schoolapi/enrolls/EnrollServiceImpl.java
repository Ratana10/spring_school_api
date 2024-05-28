package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentRepository;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.students.Student;
import com.piseth.schoolapi.students.StudentService;
import com.piseth.schoolapi.students.StudentType;
import com.piseth.schoolapi.utils.PaymentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollRepository enrollRepository;
    private final EnrollMapper enrollMapper;
    private final PaymentUtil paymentUtil;
    private final PaymentRepository paymentRepository;

    @Override
    public List<EnrollDTO> create(EnrollRequest enrollRequest) {

        //Mapping enroll
        List<EnrollDTO> listEnrollDTO = enrollRequest.getCourseIds().stream()
                .map(courseId -> EnrollDTO.builder()
                        .studentId(enrollRequest.getStudentId())
                        .courseId(courseId)
                        .enrollDate(enrollRequest.getEnrollDate())
                        .build()
                ).toList();

        //mapping dto to enroll
        List<Enroll> enrolls = enrollMapper.toEnrollList(listEnrollDTO);

        Student student = enrolls.get(0).getStudent();

        List<String> errors = new ArrayList<>();
        List<Enroll> savedEnroll = new ArrayList<>();
        List<Enroll> newEnroll = new ArrayList<>();

        for (Enroll enroll : enrolls) {
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

        //if no error => create enroll and payment
        for (Enroll enroll : savedEnroll) {

            BigDecimal cashback = BigDecimal.valueOf(0);
            enroll = enrollRepository.save(enroll);

            //have amount => make payment
            if (BigDecimal.ZERO.compareTo(enrollRequest.getAmount()) != 0) {

                Payment payment = Payment.builder()
                        .enroll(enroll)
                        .amount(enrollRequest.getAmount())
                        .paymentType(enrollRequest.getPaymentType())
                        .paymentDate(LocalDate.from(enrollRequest.getEnrollDate()))
                        .build();

                cashback = paymentUtil.makePayment(payment, enroll);

                //save payment
                paymentRepository.save(payment);
                //update enroll
                enroll = enrollRepository.save(enroll);


                //update amount
                enrollRequest.setAmount(cashback);
            }

            newEnroll.add(enroll);
        }

        return  newEnroll.stream().
                map(enrollMapper::toEnrollDTO)
                .toList();
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

    private BigDecimal checkCoursePrice(StudentType studyType, Course course) {
        if (studyType == StudentType.STUDY) {
            return course.getStudentPrice();
        }

        return course.getNormalPrice();
    }


    private String checkStudentEnrollTheCourse(Long studentId, Long courseId) {
        //check student enrolled
        Boolean temp = enrollRepository.existsByStudentIdAndCourseId(studentId, courseId);

        return temp ?
                String.format("StudentId=%d enrolled the courseId=%d already", studentId, courseId)
                : null;

    }
}
