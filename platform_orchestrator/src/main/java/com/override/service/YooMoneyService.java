package com.override.service;

import com.override.feign.YooMoneyApiFeign;
import com.override.model.Payment;
import com.override.repository.PaymentRepository;
import dto.YooMoneyConfirmationRequestDTO;
import dto.YooMoneyConfirmationResponseDTO;
import dto.YooMoneyRequestInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class YooMoneyService {

    @Value("${yooMoney.authorization}")
    private String authorization;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private YooMoneyApiFeign yooMoneyApiFeign;

    public YooMoneyConfirmationRequestDTO createYooMoneyConfirmationRequestDTO(YooMoneyRequestInfoDTO yooMoneyRequestInfoDTO) {
        return YooMoneyConfirmationRequestDTO
                .builder()
                .amount(YooMoneyConfirmationRequestDTO.Amount.builder().currency("RUB").value(yooMoneyRequestInfoDTO.getAmount()).build())
                .confirmation(YooMoneyConfirmationRequestDTO.Confirmation.builder().type("embedded").build())
                .capture(true)
                .description(yooMoneyRequestInfoDTO.getComment())
                .merchant_customer_id(yooMoneyRequestInfoDTO.getLogin())
                .build();
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 1800000)
    private void tryToUpdatePayments() {
        List<Payment> listOfPayments = paymentRepository.getAllPaymentIdsWithPendingStatus();
        for (Payment payment : listOfPayments) {
            YooMoneyConfirmationResponseDTO responseDTO = yooMoneyApiFeign.getPaymentInfo(payment.getPaymentId(), authorization);
            if (responseDTO.getStatus().equals("succeeded")) {
                payment.setStatus(responseDTO.getStatus());
                paymentRepository.save(payment);
            }
            if (responseDTO.getStatus().equals("canceled")) {
                paymentRepository.delete(payment);
            }
        }
    }

    public Integer getRandomInteger() {
        Random random = new Random();
        return random.nextInt();
    }
}
