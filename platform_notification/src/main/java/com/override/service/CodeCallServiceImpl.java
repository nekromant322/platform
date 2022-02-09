package com.override.service;

import com.override.feign.SmsRuFeign;
import dtos.CodeCallResponseDTO;
import dtos.BalanceResponseFromSmsRuFeignClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CodeCallServiceImpl implements CodeCallService {
    @Value("${sms.api.id}")
    private String apiID;

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
    public double getBalance() {
        BalanceResponseFromSmsRuFeignClientDTO balanceResponseFromSmsRuFeignClientDTO = smsRuFeign.getBalance(apiID);
        return balanceResponseFromSmsRuFeignClientDTO.getBalance();
    }
}
