package com.piseth.schoolapi.payments;

import com.piseth.schoolapi.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody PaymentDTO paymentDTO) {

        Payment payment = paymentMapper.toPayment(paymentDTO);
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

    @DeleteMapping("{id}/cancel")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        paymentService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete payment successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

}
