package com.override.mapper;

import com.override.model.Payment;
import dto.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment dtoToEntity(PaymentDTO paymentDTO, String username) {
        return new Payment(username, paymentDTO.getDate(), paymentDTO.getSum(), paymentDTO.getAccountNumber(),
                paymentDTO.getComment());
    }
}
