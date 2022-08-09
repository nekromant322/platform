package com.override.controller.rest;

import com.override.service.StatisticsService;
import dto.CodeTryStatDTO;
import dto.GeneralIncomeDTO;
import dto.IncomeFromUsersDTO;
import dto.SalaryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("statistics")
public class StatisticsRestController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/hardTasks")
    @ApiOperation(value = "Возвращает мапу Map<String, Long>, где ключ(String) - номер той самой сложной " +
            "задачи (пример: 4.2.1, где 4 - глава, 2 - шаг, 1 - урок), а значение(Long) - колчичество неудачных " +
            "решений данной задачи")
    public Map<String, Long> statList(@RequestParam int size){
        return statisticsService.countStatsOfHardTasks(size);
    }

    @GetMapping("/data")
    @ApiOperation(value = "Возвращает CodeTryStatDTO")
    public CodeTryStatDTO countStatsByStatus(@RequestParam int size){
        return statisticsService.getCodeTryStatistics(size);
    }

    @GetMapping("/salary")
    @ApiOperation(value = "Возвращает SalaryDTO")
    public SalaryDTO getSalaryStat(){
        return statisticsService.getSalaryStatistics();
    }

    @GetMapping("/allPayment")
    @ApiOperation(value = "Возвращает IncomeFromUsersDTO")
    public IncomeFromUsersDTO getAllPayment(){
        return statisticsService.getAllPayment();
    }

    @GetMapping("/generalIncome")
    @ApiOperation(value = "Возвращает GeneralIncomeDTO")
    public GeneralIncomeDTO getGeneralPayment(){
        return statisticsService.getGeneralPayment();
    }
}
