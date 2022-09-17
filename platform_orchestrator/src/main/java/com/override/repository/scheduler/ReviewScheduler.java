package com.override.repository.scheduler;

import com.override.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

@Service
public class ReviewScheduler {

    @Autowired
    private ReviewService reviewService;

    /**
     * Ревью можно назначить на чч:00 или на чч:30.
     * Оповещает участников ревью за 5 минут до начала.
     */
    @Schedules({@Scheduled(cron = "0 25 * * * *"), @Scheduled(cron = "0 55 * * * *")})
    public void checkBookedReviewEveryHalfHour() {
        reviewService.sendScheduledNotification();
    }
}
