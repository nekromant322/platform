package com.override.service;

public interface SmsRuService {

    String verifyNumber(String clientPhoneNumber);

    double getBalance();

    void sendSms(String phoneNumber, String message);
}
