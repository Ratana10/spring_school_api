package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody PaymentDTO paymentDTO) {

        Payment payment = PaymentMapper.INSTANCE.toPayment(paymentDTO);
        payment = paymentService.create(payment);

        ApiResponse response = ApiResponse.builder()
                .data(PaymentMapper.INSTANCE.toPaymentDTO(payment))
                .message("create payment successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
