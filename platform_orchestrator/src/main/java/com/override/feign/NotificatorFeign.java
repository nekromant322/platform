package com.override.feign;

import dto.MailDTO;
import dto.RecipientDTO;
import enums.Communication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification")
public interface NotificatorFeign {

    @GetMapping("/calls/balance")
    Double getBalance();

    @PostMapping("/calls/code")
    String callToClient(@RequestBody String phoneNumber);

    @GetMapping("/recipient/get/{login}")
    RecipientDTO getRecipient(@PathVariable("login") String login);

    @GetMapping("/recipient/getSecurityCode/{login}")
    String getSecurityCode(@PathVariable("login") String login);

    @GetMapping("/recipient/getVkChatId/{login}")
    Integer getVkChatID(@PathVariable String login);

    @PostMapping("/recipients/setCommunication")
    void setCommunications(
            @RequestParam("login") String login,
            @RequestParam("value") String value,
            @RequestParam("type") Communication type);

    @PostMapping("/recipients/save")
    void saveRecipient(@RequestBody RecipientDTO recipientDTO);

    @PostMapping("/recipients/delete")
    void deleteRecipient(@RequestBody RecipientDTO recipientDTO);

    @PostMapping("/communications")
    void sendMessage(
            @RequestParam("login") String login,
            @RequestParam("message") String message,
            @RequestParam("type") Communication... type);

    @PostMapping("/email")
    void sendMessageByMail(@RequestBody MailDTO mailDTO);
}
