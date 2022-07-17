package com.override.service;

import com.override.mapper.PaymentMapper;
import com.override.model.Payment;
import com.override.repository.PaymentRepository;
import dto.PaymentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestPayment;
import static com.override.utils.TestFieldsUtil.generateTestPaymentDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Test
    public void testSave() {
        PaymentDTO paymentDTO = generateTestPaymentDTO();
        Payment payment = generateTestPayment();

        paymentService.save(payment);
        paymentService.save(paymentDTO, payment.getStudentName());

        verify(paymentRepository, times(2)).save(any());
    }

    @Test
    public void testGetAll() {
        paymentService.getAll();

        verify(paymentRepository, times(1)).findAll();
    }
}