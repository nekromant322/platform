package com.override.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.CodeCallResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CodeCallServiceImpl implements CodeCallService {
    @Value("${sms.api.id}")
    private String apiID;

    @Value("${sms.url}")
    private String url;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    @Autowired
    CodeCallServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public String sendGet(String clientPhoneNumber) {
        String securityCode = "-1";
        ResponseEntity<String> response = restTemplate.getForEntity(
                url + "?phone=" + clientPhoneNumber + "&api_id=" + apiID,
                String.class);
        System.out.println(response.getBody());
        try {
            CodeCallResponseDTO codeCallResponseDTO = objectMapper.readValue(response.getBody(), CodeCallResponseDTO.class);
            securityCode = codeCallResponseDTO.getCode();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return securityCode;
    }
}
