package com.override.service;


import com.override.feign.VkApiFeign;
import com.override.model.VkCall;
import com.override.repository.VkCallRepository;
import dto.VkActorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.override.utils.TestFieldsUtil.generateTestVkCall;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VkApiServiceTest {

    @InjectMocks
    private VkApiService vkApiService;

    @Mock
    private VkApiFeign vkApiFeign;

    @Mock
    private VkCallRepository vkCallRepository;

    @Test
    public void getCallTest() {

        VkCall testVkCall = generateTestVkCall();

        when(vkCallRepository.findVkCallByReviewId(any())).thenReturn(testVkCall);

        assertEquals(vkApiService.getCall(any()), "joinLink");
    }

    @Test
    public void createVkCallTest() {

        ResponseEntity<String>  responseEntity = new ResponseEntity<>(
                "{'response':{'call_id':'123', 'join_link':'link'}}", HttpStatus.OK);
        VkCall vkCall = VkCall.builder().callId("123").joinLink("link").reviewId(1L).build();
        VkActorDTO vkActorDTO = VkActorDTO.builder().build();

        when(vkApiFeign.createCall(any(), any())).thenReturn(responseEntity);

        vkApiService.createVkCall(1L, vkActorDTO);

        verify(vkCallRepository, times(1)).save(vkCall);
    }
}