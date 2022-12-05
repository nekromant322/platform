package com.override.service;

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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
    private CacheManager cacheManager;

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

    public synchronized Integer getVkChatIdFromBot() throws InterruptedException, ClientException, ApiException {
        Boolean[] stop = {false};
        Integer[] result = {null};
        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        GroupActor actor = new GroupActor(groupId, vkToken);
        Random random = new Random();
        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        while (true) {
            try {
                MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
                List<Message> messages = historyQuery.execute().getMessages().getItems();
                if (!messages.isEmpty()) {
                    messages.forEach(message -> {
                        System.out.println(message.toString());
                        try {
                            if (message.getText().contains("/code")) {
                                String code = message.getText().substring(6);
                                if (getLoginFromCache(code) == null) {
                                    vk.messages().send(actor).message("Вы ввели неверный код.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                                } else {
                                    vk.messages().send(actor).message("Уведомления успешно подключены для пользователя " + getLoginFromCache(code) + ".").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                                    stop[0] = true;
                                    result[0] = message.getFromId();
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
        return result[0];
    }

    public String getCode(String login) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(9));
        }
        Cache data = cacheManager.getCache("vkSecurityCode");
        data.put(stringBuilder.toString(), login);
        return stringBuilder.toString();
    }

    public String getLoginFromCache(String code) {
        Cache data = cacheManager.getCache("vkSecurityCode");
        if (data.get(code) == null) {
            return null;
        }
        Cache.ValueWrapper cacheCode = data.get(code);
        String securityCode = (String) cacheCode.get();
        return securityCode;
    }

    public Integer getVkChatId(String login) throws ClientException, InterruptedException, ApiException {
        if (recipientService.findRecipientByLogin(login).getVkChatId().isEmpty()) {
            return getVkChatIdFromBot();
        }
        return null;
    }
}
