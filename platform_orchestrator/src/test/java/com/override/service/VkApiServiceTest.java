package com.override.service;


import com.override.feign.VkApiFeign;
import com.override.model.VkCall;
import com.override.repository.VkCallRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}