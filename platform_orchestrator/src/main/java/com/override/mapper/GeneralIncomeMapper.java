package com.override.mapper;

import dto.GeneralIncomeDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GeneralIncomeMapper {

    public GeneralIncomeDTO entityToDto(List<LocalDate> labels, List<Double> income) {
        return GeneralIncomeDTO.builder()
                .dataMonth(labels)
                .income(income)
                .build();
    }
}
