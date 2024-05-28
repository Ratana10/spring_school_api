package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.enrolls.EnrollService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {EnrollService.class}
)
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "enrollId", target = "enroll")
    Payment toPayment(PaymentDTO enrollDTO);

    @Mapping(source = "enroll.id", target = "enrollId")
    PaymentDTO toPaymentDTO(Payment enroll);

}
