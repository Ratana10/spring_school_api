package com.piseth.schoolapi.enrolls;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.courses.CourseService;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentRepository;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.promotion.Promotion;
import com.piseth.schoolapi.promotion.PromotionService;
import com.piseth.schoolapi.students.Student;
import com.piseth.schoolapi.students.StudentService;
import com.piseth.schoolapi.utils.CourseUtil;
import com.piseth.schoolapi.utils.EnrollUtil;
import com.piseth.schoolapi.utils.PaymentUtil;
import com.piseth.schoolapi.utils.PromotionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EnrollServiceImpl implements EnrollService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollRepository enrollRepository;
    private final EnrollMapper enrollMapper;
    private final PaymentRepository paymentRepository;
    private final PromotionService promotionService;

    private final EnrollUtil enrollUtil;
    private final PromotionUtil promotionUtil;
    private final CourseUtil courseUtil;

    @Override
    public List<EnrollDTO> create(EnrollRequest enrollRequest) {
        Enroll enroll = enrollMapper.reqtoEnroll(enrollRequest);

        Student student = enroll.getStudent();

        List<String> errors = validateStudentEnrolledTheCourses(student.getId(), enrollRequest.getCourseIds());
        if (!errors.isEmpty()) {
            throw new ApiException(String.join(";", errors), HttpStatus.BAD_REQUEST);
        }

        //create enrolls entity
        List<Enroll> enrolls = enrollRequest.getCourseIds().stream()
                .map(courseId -> {
                    Course course = courseService.getById(courseId);
                    BigDecimal coursePrice = courseUtil.checkCoursePrice(student.getStudentType(), course);

                    return Enroll.builder()
                            .price(coursePrice)
                            .remain(coursePrice)
                            .student(student)
                            .course(course)
                            .paymentStatus(PaymentStatus.UNPAID)
                            .enrollDate(enrollRequest.getEnrollDate())
                            .build();
                }).toList();

        List<Enroll> enrollList = new ArrayList<>(enrolls);

        if (enrollRequest.getPromotionId() != null) {
            Promotion promotion = promotionService.getById(enrollRequest.getPromotionId());
            if (promotion != null) {
                promotionUtil.applyPromotion(enrollRequest, student, enrollList, promotion);
            }
        }

        //save enroll
        enrollList = enrollRepository.saveAll(enrollList);


        // make payment
        BigDecimal amount = enrollRequest.getAmount();
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            for (Enroll enr : enrollList) {

                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    Payment payment = Payment.builder()
                            .enroll(enr)
                            .amount(amount)
                            .paymentType(enrollRequest.getPaymentType())
                            .paymentDate(LocalDate.from(enr.getEnrollDate()))
                            .build();

                    //save payment
                    payment = paymentRepository.save(payment);

                    //update enroll payment status and remain
                    //update amount
                    amount = enrollUtil.updateEnrollPaymentStatusAndRemain(enr, payment);

                }
            }
        }

        return enrollList.stream().
                map(enrollMapper::toEnrollDTO)
                .toList();
    }

    private List<String> validateStudentEnrolledTheCourses(Long studentId, List<Long> courseIds) {
        return courseIds.stream()
                .map(courseId -> enrollUtil.checkStudentEnrollTheCourse(studentId, courseId))
                .filter(Objects::nonNull)
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

}
