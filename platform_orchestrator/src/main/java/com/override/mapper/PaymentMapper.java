package com.override.mapper;

import com.override.model.Payment;
import dto.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment dtoToEntity(PaymentDTO paymentDTO, String username) {
        return Payment.builder()
                .studentName(username)
                .date(paymentDTO.getDate())
                .sum(paymentDTO.getSum())
                .accountNumber(paymentDTO.getAccountNumber())
                .message(paymentDTO.getComment())
                .status(paymentDTO.getStatus())
                .paymentId(paymentDTO.getPaymentId())
                .build();
    }
}
