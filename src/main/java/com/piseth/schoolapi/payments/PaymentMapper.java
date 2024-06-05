package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrollment.EnrollmentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {
                PaymentService.class,
                EnrollmentService.class
        }
)
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "enrollmentId", target = "enrollment")
    Payment toPayment(PaymentDTO enrollDTO);

    PaymentDTO toPaymentDTO(Payment enroll);

}
