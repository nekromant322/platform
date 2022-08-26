package com.override.scheduler;

import com.override.service.ReviewAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReviewAlertScheduler {

    @Autowired
    private ReviewAlertService reviewAlertService;

    /**
     * Оповещения для менторов о студентах, которые давно не приходили на ревью
     */

    @Scheduled(cron = "${reviewAlert.cronNotificationPeriodForStudentsWithNoNewSolutions}")
    public void alertCoreStudentsWithLowActivityAndMentors() {
        reviewAlertService.alertBadStudents();
    }
}
