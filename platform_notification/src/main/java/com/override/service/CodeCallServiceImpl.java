package com.override.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.CodeCallResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

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

    //TODO нам приходит код подтверждения, но надо бы его передать куда-то, для валидации правильно ли юзер ввел.
    @Override
    public void sendGet(String clientPhoneNumber) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                url + "?phone=" + clientPhoneNumber + "&api_id=" + apiID,
                String.class);
        System.out.println(response.getBody());
        try {
            CodeCallResponseDTO codeCallResponseDTO = objectMapper.readValue(response.getBody(), CodeCallResponseDTO.class);
            System.out.println("This is codeCallResponseDTO.code: \n" + codeCallResponseDTO.getCode());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
