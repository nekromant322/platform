package com.override.mapper;

import dto.IncomeFromUsersDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncomeFromUsersMapper {

    public IncomeFromUsersDTO entityToDto(List<String> studentNames, List<Long> sum){
        return IncomeFromUsersDTO.builder()
                .studentName(studentNames)
                .income(sum)
                .build();
    }
}
