package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.courses.Course;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.payments.Payment;
import com.piseth.schoolapi.payments.PaymentService;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.promotion.Promotion;
import com.piseth.schoolapi.promotion.PromotionService;
import com.piseth.schoolapi.students.StudentService;
import com.piseth.schoolapi.utils.CourseUtil;
import com.piseth.schoolapi.utils.PromotionUtil2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;

    private final EnrollmentRepository enrollmentRepo;

    private final PromotionService promotionService;
    private final PaymentService paymentService;
    private final StudentService studentService;

    private final CourseUtil courseUtil;
    private final PromotionUtil2 promotionUtil2;
    private final EnrollmentUtil enrollmentUtil;
    private final PaymentUtil2 paymentUtil2;


    @Override
    public EnrollmentDTO create(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentDTO);

        //Check is student enrollment the courses
        enrollmentUtil.isStudentEnrollmentTheCourses(enrollment.getStudent(), enrollmentDTO.getCourseIds());

        //Calculate total course
        BigDecimal total = enrollment.getCourses().stream()
                .map(cou -> courseUtil.checkCoursePrice(enrollment.getStudent().getStudentType(), cou))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        enrollment.setPaymentStatus(PaymentStatus.UNPAID);
        enrollment.setAmount(total);
        enrollment.setRemain(total);

        //check and apply promotion
        if (enrollmentDTO.getPromotionId() != null) {
            Promotion promotion = promotionService.getById(enrollmentDTO.getPromotionId());
            if (promotion != null) {
                promotionUtil2.applyPromotion(enrollment, promotion);
            }
        }

        // save the enrollment
        enrollmentRepo.save(enrollment);

        BigDecimal cashback = BigDecimal.ZERO;
        //check and apply payment
        if (enrollmentDTO.getAmount() != null
                && enrollmentDTO.getAmount().compareTo(BigDecimal.ZERO) > 0) {

            cashback = paymentUtil2.makePayment(
                    enrollment,
                    enrollmentDTO.getAmount(),
                    enrollmentDTO.getPaymentType(),
                    LocalDate.from(enrollmentDTO.getEnrollDate())
            );

        }

        return enrollmentMapper.toEnrollmentDTO(enrollment);
    }

    @Override
    public void delete(Long id) {
        getById(id);

        paymentService.findByEnrollmentId(id)
                .stream()
                .map(Payment::getId)
                .forEach(paymentService::delete);

        enrollmentRepo.deleteById(id);
    }

    @Override
    public Enrollment getById(Long id) {
        return enrollmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", id));
    }

    @Override
    public Enrollment findStudentIdAndCourseId(Long studentId, Long courseId) {
        return enrollmentRepo.findByStudentIdAndCourseId(studentId, courseId)
                .orElse(null);
    }

    @Override
    public List<Enrollment> findStudentIdAndCourseIds(Long studentId, Set<Long> courseIds) {
        return enrollmentRepo.findByStudentIdAndCourseIds(studentId, courseIds);
    }

    @Override
    public List<Course> getCourseEnrollmentByStudentId(Long studentId) {
        //search student
        studentService.getById(studentId);
        List<Enrollment> enrollmentsById = enrollmentRepo.findByStudentId(studentId);
        List<Course> list = enrollmentsById.stream()
                .flatMap(enr -> enr.getCourses().stream())
                .distinct()
                .toList();

        return list;
    }

}
