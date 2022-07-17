package com.override.mapper;

import com.override.model.Payment;
import dto.PaymentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestPayment;
import static com.override.utils.TestFieldsUtil.generateTestPaymentDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaymentMapperTest {

    @InjectMocks
    private PaymentMapper paymentMapper;

    @Test
    public void testDtoToEntity() {
        Payment payment = generateTestPayment();
        PaymentDTO paymentDTO = generateTestPaymentDTO();

        Payment payment1 = paymentMapper.dtoToEntity(paymentDTO, payment.getStudentName());

        assertEquals(payment1, payment);

    }
}