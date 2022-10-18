package com.override.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import dto.MessageDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class VkService {

    @Value("${vk.groupId}")
    private Integer groupId;
    @Value("${vk.token}")
    private String vkToken;

    public void sendMessage(MessageDTO message) {
        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        GroupActor actor = new GroupActor(groupId, vkToken);
        try {
            vk.messages()
                    .send(actor)
                    .message(message.getMessage())
                    .userId(Integer.parseInt(message.getChatId()))
                    .randomId(new Random().nextInt(10000))
                    .execute();
        } catch (ApiException | ClientException | FeignException e) {
            log.error("При попытке отправить сообщение произошла ошибка \"{}\"", e.getMessage());
        }
    }
}
