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
public class VkService extends Thread{

    @Value("${vk.groupId}")
    private Integer groupId;

    @Value("${vk.token}")
    private String vkToken;

    @Autowired
    private RecipientService recipientService;

    @Autowired
    private CacheManager cacheManager;

    VkApiClient vk = new VkApiClient(new HttpTransportClient());

    GroupActor actor = new GroupActor(groupId, vkToken);

    Random random = new Random();

    public void sendMessage(MessageDTO message) {
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

    public void enableBot() throws InterruptedException, ClientException, ApiException {
        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        while (true) {
            try {
                MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
                List<Message> messages = historyQuery.execute().getMessages().getItems();
                if (!messages.isEmpty()) {
                    for(Message message : messages) {
                            try {
                                if (message.getText().contains("/code")) {
                                    String code = message.getText().substring(6);
                                    Cache data = cacheManager.getCache("vkChatId");
                                    data.put(code, message.getFromId());
                                } else {
                                    vk.messages().send(actor).message("Я тебя не понял.").userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
                                }
                            } catch (ApiException | ClientException e) {
                                e.printStackTrace();
                            }
                    }
                }
            } catch (ApiException | ClientException | FeignException e) {
                log.error("При попытке отправить сообщение произошла ошибка \"{}\"", e.getMessage());
            }
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);
        }
    }

    public String generateCode(String login) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(9));
        }
        Cache data = cacheManager.getCache("vkSecurityCode");
        data.put(login, stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String getChatIdByCode(String code) {
        Cache data = cacheManager.getCache("vkChatId");
        if (data.get(code) == null) {
            return null;
        }
        Cache.ValueWrapper cacheCode = data.get(code);
        String chatId = (String) cacheCode.get();
        return chatId;
    }

    public String getSecurityCodeByLogin(String login){
        Cache data = cacheManager.getCache("vkSecurityCode");
        if (data.get(login) == null) {
            throw new NullPointerException();
        }
        Cache.ValueWrapper cacheCode = data.get(login);
        String securityCode = (String) cacheCode.get();
        return securityCode;
    }

    public String getVkChatId(String login){
       String code = getSecurityCodeByLogin(login);
       return getChatIdByCode(code);
    }

    @Override
    public void run() {
        try {
            enableBot();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
