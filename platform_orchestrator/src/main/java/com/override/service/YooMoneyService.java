package com.override.service;

import com.override.feign.YooMoneyApiFeign;
import com.override.model.Payment;
import com.override.repository.PaymentRepository;
import dto.YooMoneyConfirmationRequestDTO;
import dto.YooMoneyConfirmationResponseDTO;
import dto.YooMoneyRequestInfoDTO;
import enums.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class YooMoneyService {

    @Value("${yooMoney.authorization}")
    private String authorization;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private YooMoneyApiFeign yooMoneyApiFeign;

    public YooMoneyConfirmationRequestDTO createYooMoneyConfirmationRequestDTO(YooMoneyRequestInfoDTO infoDTO) {
        return YooMoneyConfirmationRequestDTO
                .builder()
                .amount(YooMoneyConfirmationRequestDTO.Amount.builder()
                        .currency("RUB")
                        .value(infoDTO.getAmount())
                        .build())
                .confirmation(YooMoneyConfirmationRequestDTO.Confirmation.builder().type("embedded").build())
                .capture(true)
                .description(infoDTO.getComment())
                .merchantCustomerId(infoDTO.getLogin())
                .build();
    }

    @Scheduled(initialDelayString = "${yooMoney.initialDelay}", fixedDelayString = "${yooMoney.fixedDelay}")
    public void tryToUpdatePayments() {
        List<Payment> listOfPayments = paymentRepository.getByStatus(PaymentStatus.PENDING.getName());
        for (Payment payment : listOfPayments) {
            YooMoneyConfirmationResponseDTO response;
            try {
                response = yooMoneyApiFeign.getPaymentInfo(payment.getPaymentId(), authorization);
            } catch (Exception e) {
                log.warn("При проверки платежа № " + payment.getPaymentId() + " произошла ошибка " + e.getClass());
                continue;
            }

            if (response.getStatus().equals(PaymentStatus.SUCCEEDED.getName())) {
                payment.setStatus(response.getStatus());
                paymentRepository.save(payment);
            }
            if (response.getStatus().equals(PaymentStatus.CANCELED.getName())) {
                paymentRepository.delete(payment);
            }
        }
    }

    public YooMoneyConfirmationResponseDTO getConfirmationResponseDTO(YooMoneyRequestInfoDTO yooMoneyRequestInfoDTO) {
        Random random = new Random();
        YooMoneyConfirmationRequestDTO request = createYooMoneyConfirmationRequestDTO(yooMoneyRequestInfoDTO);
        return yooMoneyApiFeign.getConfirmationResponse(random.nextInt(), authorization, request);
    }
}
