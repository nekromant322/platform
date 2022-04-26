package com.override.service;

public interface SmsRuService {

    String verifyNumber(String clientPhoneNumber);

    double getBalance();

    String sendSms(String phoneNumber, String message);
}
