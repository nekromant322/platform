package com.override.service;

import com.override.feign.TelegramBotFeign;
import com.override.mapper.JoinRequestMapper;
import com.override.mapper.PlatformUserMapper;
import com.override.model.JoinRequest;
import com.override.model.PlatformUser;
import com.override.repository.JoinRequestRepository;
import dto.JoinRequestStatusDTO;
import dto.RegisterUserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class JoinRequestServiceTest {

    @InjectMocks
    private JoinRequestService joinRequestService;

    @Mock
    private JoinRequestRepository requestRepository;

    @Mock
    private TelegramBotFeign telegramBotFeign;

    @Mock
    private JoinRequestMapper joinRequestMapper;

    @Mock
    private PlatformUserService accountService;

    @Mock
    private PlatformUserMapper accountMapper;


    @Test
    public void testSaveRequestWhenAlreadyExistJoinRequest() {
        RegisterUserRequestDTO requestDTO = RegisterUserRequestDTO.builder()
                .telegramUserName("Marandyuk_Anatolii")
                .chatId("1234")
                .build();
        when(requestRepository.findFirstByChatId("1234")).thenReturn(new JoinRequest(1L, "Marandyuk_Anatolii", "1234"));

        JoinRequestStatusDTO joinRequestStatusDTO = joinRequestService.saveRequest(requestDTO);

        assertEquals(new JoinRequestStatusDTO("В этом чате уже есть запрос на регистрацию"), joinRequestStatusDTO);
    }


    @Test
    public void testSaveRequestWhenAlreadyExistUserInPlatform() {
        RegisterUserRequestDTO requestDTO = RegisterUserRequestDTO.builder()
                .telegramUserName("Marandyuk_Anatolii")

                .chatId("1234")
                .build();

        when(requestRepository.findFirstByChatId("1234")).thenReturn(null);
        when(accountService.findPlatformUserByLogin("Marandyuk_Anatolii")).thenReturn(new PlatformUser());


        JoinRequestStatusDTO joinRequestStatusDTO = joinRequestService.saveRequest(requestDTO);

        assertEquals(new JoinRequestStatusDTO("Вы уже зарегистрированы"), joinRequestStatusDTO);
    }

    @Test
    public void testSaveRequestWhenNewUser() {
        RegisterUserRequestDTO requestDTO = RegisterUserRequestDTO.builder()
                .telegramUserName("Marandyuk_Anatolii")
                .chatId("1234")
                .build();
        JoinRequest requestEntity = new JoinRequest(1L, "Marandyuk_Anatolii", "1234");

        when(requestRepository.findFirstByChatId("1234")).thenReturn(null);
        when(accountService.findPlatformUserByLogin("Marandyuk_Anatolii")).thenReturn(null);
        when(joinRequestMapper.dtoToEntity(any())).thenReturn(requestEntity);

        JoinRequestStatusDTO joinRequestStatusDTO = joinRequestService.saveRequest(requestDTO);

        assertEquals(new JoinRequestStatusDTO("Ваш запрос на регистрацию в платформе создан, ожидайте подтверждения"),
                joinRequestStatusDTO);
        verify(requestRepository, times(1)).save(requestEntity);
    }
}
