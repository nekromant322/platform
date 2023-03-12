package com.override.controller.rest;

import com.override.service.StatisticsService;
import dto.CodeTryStatDTO;
import dto.GeneralIncomeDTO;
import dto.IncomeFromUsersDTO;
import dto.SalaryDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Возвращает мапу Map<String, Long>, где ключ(String) - номер той самой сложной " +
            "задачи (пример: 4.2.1, где 4 - глава, 2 - шаг, 1 - урок), а значение(Long) - колчичество неудачных " +
            "решений данной задачи")
    public Map<String, Long> statList(@RequestParam int size){
        return statisticsService.countStatsOfHardTasks(size);
    }

    @GetMapping("/data")
    @Operation(summary = "Возвращает CodeTryStatDTO, подробно описно в модели ДТОшки")
    public CodeTryStatDTO countStatsByStatus(@RequestParam int size){
        return statisticsService.getCodeTryStatistics(size);
    }

    @GetMapping("/salary")
    @Operation(summary = "Возвращает SalaryDTO, подробно описно в модели ДТОшки")
    public SalaryDTO getSalaryStat(){
        return statisticsService.getSalaryStatistics();
    }

    @GetMapping("/allPayment")
    @Operation(summary = "Возвращает IncomeFromUsersDTO, подробно описно в модели ДТОшки")
    public IncomeFromUsersDTO getAllPayment(){
        return statisticsService.getAllPayment();
    }

    @GetMapping("/generalIncome")
    @Operation(summary = "Возвращает GeneralIncomeDTO, подробно описно в модели ДТОшки")
    public GeneralIncomeDTO getGeneralPayment(){
        return statisticsService.getGeneralPayment();
    }
}
