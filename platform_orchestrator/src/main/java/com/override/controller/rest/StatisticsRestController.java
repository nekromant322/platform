package com.override.controller.rest;

import com.override.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("statistics")
public class StatisticsRestController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/hardTasks")
    public Map<String, Long> statList(@RequestParam int size){
        return statisticsService.countStatsOfHardTasks(size);
    }

    @GetMapping("/status")
    public Map<String, BigInteger> countStatsByStatus(){
        return statisticsService.countStatsByStatus();
    }

    @GetMapping("/users")
    public Map<String, BigInteger> countStatsByStatusAndUser(){
        return statisticsService.countStatsByStatusAndUser();
    }

    @GetMapping("/tasks")
    public Map<String, Double> countStatsByChapterAndStepAndStatus(){
        return statisticsService.countStatsByChapterAndStepAndStatus();
    }
}
