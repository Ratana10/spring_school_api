package com.piseth.schoolapi.enrollment;

import com.piseth.schoolapi.enrolls.Enroll;
import com.piseth.schoolapi.exception.ApiException;
import com.piseth.schoolapi.exception.ResourceNotFoundException;
import com.piseth.schoolapi.payments.PaymentStatus;
import com.piseth.schoolapi.promotion.Promotion;
import com.piseth.schoolapi.promotion.PromotionService;
import com.piseth.schoolapi.utils.CourseUtil;
import com.piseth.schoolapi.utils.EnrollUtil;
import com.piseth.schoolapi.utils.PromotionUtil2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;

    private final EnrollmentRepository enrollmentRepo;

    private final PromotionService promotionService;

    private final EnrollUtil enrollUtil;
    private final CourseUtil courseUtil;
    private final PromotionUtil2 promotionUtil2;

    @Override
    public List<EnrollmentDTO> create(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentDTO);

        List<String> errors = enrollUtil.validateStudentEnrolledTheCourses(
                enrollmentDTO.getStudentId(),
                enrollmentDTO.getCourseIds()
        );

        if (!errors.isEmpty()) {
            throw new ApiException(String.join(";", errors), HttpStatus.BAD_REQUEST);
        }


        BigDecimal amount = enrollment.getCourses().stream()
                .map(cou -> courseUtil.checkCoursePrice(enrollment.getStudent().getStudentType(), cou) )
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        if (enrollmentDTO.getPromotionId() != null) {
            Promotion promotion = promotionService.getById(enrollmentDTO.getPromotionId());
            if (promotion != null) {
                promotionUtil2.applyPromotion(enrollment, promotion);
            }
        }

        enrollment.setPaymentStatus(PaymentStatus.UNPAID);
        enrollment.setAmount(amount);
        enrollment.setRemain(amount);
        enrollmentRepo.save(enrollment);

        return null;
    }

    @Override
    public void delete(Long id) {
        getById(id);
        enrollmentRepo.deleteById(id);
    }

    @Override
    public Enrollment getById(Long id) {
        return enrollmentRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Enrollment", id));
    }
}
