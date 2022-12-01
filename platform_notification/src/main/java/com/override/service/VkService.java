package com.override.service;

import com.override.mapper.RecipientMapper;
import com.override.model.Recipient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import dto.MessageDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class VkService {

    @Value("${vk.groupId}")
    private Integer groupId;
    @Value("${vk.token}")
    private String vkToken;

    @Autowired
    private RecipientService recipientService;

    @Autowired
    private RecipientMapper recipientMapper;

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

    public void getMessages(String username) throws InterruptedException, ClientException, ApiException {
        if (recipientService.findRecipientByLogin(username).getVkChatId().isEmpty()) {
            VkApiClient vk = new VkApiClient(new HttpTransportClient());
            GroupActor actor = new GroupActor(groupId, vkToken);
            Random random = new Random();
            Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
            while (true) {
                Boolean[] stop = {false};
                try {
                    MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
                    List<Message> messages = historyQuery.execute().getMessages().getItems();
                    if (!messages.isEmpty()) {
                        messages.forEach(message -> {
                            System.out.println(message.toString());
                            try {
                                if (message.getText().contains("/login")) {
                                    String login = message.getText().substring(7);
                                    if (!recipientService.isPresent(login)) {
                                        vk.messages().send(actor).message("Пользователь с таким логином не существует.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                                    } else {
                                        Recipient recipient = recipientService.findRecipientByLogin(login);
                                        recipient.setVkChatId(Integer.toString(message.getFromId()));
                                        recipientService.save(recipientMapper.entityToDto(recipient));
                                        vk.messages().send(actor).message("Уведомления успешно подключены для пользователя " + login + ".").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                                        stop[0] = true;
                                    }
                                } else {
                                    vk.messages().send(actor).message("Я тебя не понял.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                                }
                            } catch (ApiException | ClientException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (ApiException | ClientException | FeignException e) {
                    log.error("При попытке отправить сообщение произошла ошибка \"{}\"", e.getMessage());
                }
                ts = vk.messages().getLongPollServer(actor).execute().getTs();
                if (stop[0]) {
                    break;
                }
                Thread.sleep(500);
            }
        }
    }
}
