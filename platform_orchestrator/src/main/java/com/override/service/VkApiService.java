package com.override.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.override.feign.VkApiFeign;
import com.override.model.VkCall;
import com.override.repository.VkCallRepository;
import dto.VkActorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class VkApiService {

    @Autowired
    private VkApiFeign vkApiFeign;

    @Autowired
    private VkCallRepository vkCallRepository;

    @Value("${vk.api.version}")
    private String VERSION_VK_API;

    public String getCall(Long reviewId) {

        VkCall vkCall = vkCallRepository.findVkCallByReviewId(reviewId);

        //Изменить на модель VkCall
        return vkCall.getJoinLink();
    }

    public void createVkCall(Long reviewId, VkActorDTO vkActorDTO) {

        ResponseEntity<String> response = vkApiFeign.createCall(vkActorDTO.getAccessToken(), VERSION_VK_API);

        VkCall vkCall = parseToVkCall(response);

        vkCall.setReviewId(reviewId);

        saveCall(vkCall);

        //После сохраннеия в БД принудительное завершения звонка необходимо, чтобы их не плодить
        vkApiFeign.finishCall(vkActorDTO.getAccessToken(), vkCall.getCallId(), VERSION_VK_API);
    }

    private VkCall parseToVkCall(ResponseEntity<String> responseEntity) {

        String json = responseEntity.getBody();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonObject jsonResponseObject = jsonObject.get("response").getAsJsonObject();

        VkCall vkCall = VkCall.builder()
                .joinLink(jsonResponseObject.get("join_link").getAsString())
                .callId(jsonResponseObject.get("call_id").getAsString())
                .build();

        return vkCall;
    }

    private void saveCall(VkCall vkCall) { vkCallRepository.save(vkCall); }
}
