package com.override.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

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
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
