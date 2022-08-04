package com.override.scheduler;

import com.override.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AlertScheduler {

    @Autowired
    private AlertService alertService;

    /**
     * Оповещения для студентов, которые не присылали новых решений по кору в последнее время +
     * Оповещения для менторов об этих студентах.
     */

    @Scheduled(cron = "${alert.cronNotificationPeriodForStudentsWithNoNewSolutions}")
    public void alertCoreStudentsWithLowActivityAndMentors() {
        alertService.alertBadStudents();
    }
}

