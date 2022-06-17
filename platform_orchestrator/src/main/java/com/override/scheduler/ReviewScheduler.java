package com.override.scheduler;

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
     * The review can be scheduled at hh:00 or at hh:30.
     * The task of the scheduler is to warn all participants 5 minutes before the review.
     */
    @Schedules({@Scheduled(cron = "0 25 * * * *"), @Scheduled(cron = "0 55 * * * *")})
    public void checkBookedReviewEveryHalfHour() {
        reviewService.sendScheduledNotification();
    }
}
