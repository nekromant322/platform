package com.override.feign;

import dto.SmsResponseDTO;
import dto.SmsRuBalanceResponseDTO;
import dto.CodeCallResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="smsRuFeignClient", url="${sms.url}")
public interface SmsRuFeign {
    @GetMapping("/code/call?phone={phone}&api_id={apiID}&json=1")
    CodeCallResponseDTO verifyPhone(@PathVariable String phone, @PathVariable String apiID);

    @GetMapping("/my/balance?api_id={apiID}&json=1")
    SmsRuBalanceResponseDTO getBalance(@PathVariable String apiID);

    @GetMapping("/sms/send?api_id={apiID}&to={phoneNumber}&msg={message}&json=1")
    SmsResponseDTO sendSms(@PathVariable String phoneNumber, @PathVariable String message, @PathVariable String apiID);
}
