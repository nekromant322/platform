package com.override.mappers;

import dtos.SalaryDTO;
import dtos.SalaryStatDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaryStatMapper {
    public SalaryStatDTO entityToDTO(String userLogin,List<Integer> salaries){
        return SalaryStatDTO.builder()
                .label(userLogin)
                .data(salaries)
                .borderWidth(1)
                .build();
    }

    public SalaryDTO salaryStaDtoToSalaryDto(List<LocalDate> localDate ,List<SalaryStatDTO> salaryStatDTO){
        return SalaryDTO.builder()
                .labels(localDate.stream().map(LocalDate::toString).collect(Collectors.toList()))
                .userStats(salaryStatDTO)
                .build();
    }
}
