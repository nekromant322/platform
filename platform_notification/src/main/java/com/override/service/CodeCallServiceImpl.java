package com.override.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.override.exception.GetBalanceException;
import com.override.exception.VerifyCallException;
import com.override.feign.SmsRuFeign;
import dtos.CodeCallResponseDTO;
import dtos.BalanceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CodeCallServiceImpl implements CodeCallService {
    @Value("${sms.api.id}")
    private String apiID;

    @Value("${sms.url}")
    private String url;

    private final SmsRuFeign smsRuFeign;

    @Autowired
    public CodeCallServiceImpl(SmsRuFeign smsRuFeign) {
        this.smsRuFeign = smsRuFeign;
    }

    @Override
    public String verifyNumber(String clientPhoneNumber) {
        CodeCallResponseDTO codeCallResponseDTO = smsRuFeign.verifyPhone(clientPhoneNumber, apiID);
        return codeCallResponseDTO.getCode();
    }

    @Override
    public String getBalance() {
        BalanceResponseDTO balanceResponseDTO = smsRuFeign.getBalance(apiID);
        return balanceResponseDTO.getBalance();
    }
}
