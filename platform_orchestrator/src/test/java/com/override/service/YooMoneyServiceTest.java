package com.override.service;

import com.override.feign.YooMoneyApiFeign;
import com.override.model.Payment;
import com.override.repository.PaymentRepository;
import dto.YooMoneyConfirmationResponseDTO;
import dto.YooMoneyRequestInfoDTO;
import enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class YooMoneyServiceTest {

    @Value("${yooMoney.authorization}")
    private String authorization;

    @InjectMocks
    private YooMoneyService yooMoneyService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private YooMoneyApiFeign yooMoneyApiFeign;

    @Test
    public void testCreateYooMoneyConfirmationRequestDTO() {
        YooMoneyRequestInfoDTO yooMoneyRequestInfoDTO = new YooMoneyRequestInfoDTO();
        yooMoneyRequestInfoDTO.setAmount(10.50);
        assertEquals(yooMoneyRequestInfoDTO.getAmount(),
                yooMoneyService.createYooMoneyConfirmationRequestDTO(yooMoneyRequestInfoDTO).getAmount().getValue());
    }

    @Test
    public void testTryToUpdatePaymentsWhenSucceeded() {
        Payment payment = Payment
                .builder()
                .paymentId("testPaymentId")
                .build();
        when(paymentRepository.getByStatus(PaymentStatus.PENDING))
                .thenReturn(List.of(payment));
        when(yooMoneyApiFeign.getPaymentInfo(payment.getPaymentId(), authorization))
                .thenReturn(YooMoneyConfirmationResponseDTO
                        .builder()
                        .status(PaymentStatus.SUCCEEDED)
                        .build());
        Payment paymentSucceeded = Payment
                .builder()
                .paymentId(payment.getPaymentId())
                .status(PaymentStatus.SUCCEEDED)
                .build();
        yooMoneyService.tryToUpdatePayments();
        verify(paymentRepository, times(1)).save(paymentSucceeded);
    }

    @Test
    public void testTryToUpdatePaymentsWhenCanceled() {
        Payment payment = Payment
                .builder()
                .paymentId("testPaymentId")
                .build();
        when(paymentRepository.getByStatus(PaymentStatus.PENDING))
                .thenReturn(List.of(payment));
        when(yooMoneyApiFeign.getPaymentInfo(payment.getPaymentId(), authorization))
                .thenReturn(YooMoneyConfirmationResponseDTO
                        .builder()
                        .status(PaymentStatus.CANCELED)
                        .build());
        yooMoneyService.tryToUpdatePayments();
        verify(paymentRepository, times(1)).delete(payment);
    }
}
