package com.override.controller.rest;

import com.override.service.StatisticsService;
import dtos.CodeTryStatDTO;
import dtos.SalaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/data")
    public CodeTryStatDTO countStatsByStatus(@RequestParam int size){
        return statisticsService.getCodeTryStatistics(size);
    }

    @GetMapping("/salary")
    public SalaryDTO getSalaryStat(){
        return statisticsService.getSalaryStatistics();
    }
}
