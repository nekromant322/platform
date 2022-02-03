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

//    private final RestTemplate restTemplate;
    private final SmsRuFeign smsRuFeign;
    private final ObjectMapper objectMapper;

    @Autowired
    public CodeCallServiceImpl(SmsRuFeign smsRuFeign, ObjectMapper objectMapper) {
        this.smsRuFeign = smsRuFeign;
        this.objectMapper = objectMapper;
    }

    @Override
    public String verifyNumber(String clientPhoneNumber) {
        String jsonResponse = smsRuFeign.verifyPhone(clientPhoneNumber, apiID);
        String securityCode = new String();
        CodeCallResponseDTO codeCallResponseDTO;
        try {
            codeCallResponseDTO = objectMapper.readValue(jsonResponse, CodeCallResponseDTO.class);
            securityCode = codeCallResponseDTO.getCode();
        } catch (JsonProcessingException e) {
            throw new VerifyCallException(e.getCause());
        }
//        String securityCode = "-1";
//        ResponseEntity<String> response = restTemplate.getForEntity(
//                url + "?phone=" + clientPhoneNumber + "&api_id=" + apiID,
//                String.class);
//        System.out.println(response.getBody());
//        try {
//            CodeCallResponseDTO codeCallResponseDTO = objectMapper.readValue(response.getBody(), CodeCallResponseDTO.class);
//            securityCode = codeCallResponseDTO.getCode();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return securityCode;
    }

    @Override
    public String getBalance() {
        String balance;
        try {
            BalanceResponseDTO balanceResponseDTO =  objectMapper.readValue(smsRuFeign.getBalance(apiID), BalanceResponseDTO.class) ;
            balance = balanceResponseDTO.getBalance();
        } catch (JsonProcessingException e) {
            throw new GetBalanceException(e.getCause());
        }
        return balance;
    }
}
