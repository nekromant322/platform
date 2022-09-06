package com.override.feign;

import dto.RecipientDTO;
import enums.Communication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification")
public interface NotificatorFeign {

    @GetMapping("/calls/balance")
    Double getBalance();

    @PostMapping("/calls/code")
    String callToClient(@RequestBody String phoneNumber);

    @PostMapping("/recipients/save")
    void saveRecipient(@RequestBody RecipientDTO recipientDTO);

    @PostMapping("/recipients/delete")
    void deleteRecipient(@RequestBody RecipientDTO recipientDTO);

    @PostMapping("/communications")
    void sendMessage(
            @RequestParam("login") String login,
            @RequestParam("message") String message,
            @RequestParam("type") Communication... type);
}
