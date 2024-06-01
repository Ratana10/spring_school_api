package com.piseth.schoolapi.payments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment toPayment(PaymentDTO enrollDTO);

    PaymentDTO toPaymentDTO(Payment enroll);

}
