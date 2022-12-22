package com.override.scheduler;

import com.override.service.YooMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PaymentStatusCheckScheduler {

    @Autowired
    private YooMoneyService yooMoneyService;

    /**
     * Проверяет статусы всех неподтвержденных платежей.
     */
    @Scheduled(initialDelayString = "${yooMoney.initialDelay}", fixedDelayString = "${yooMoney.fixedDelay}")
    public void tryToUpdatePayments() {
        yooMoneyService.tryToUpdatePayments();
    }
}
