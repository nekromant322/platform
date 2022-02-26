package com.override.services;

import com.override.PlatformBot;
import dtos.ResponseJoinRequestDTO;
import dtos.PlatformUserDTO;
import enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final PlatformBot platformBot;

    public void processJoinRequestResponse(ResponseJoinRequestDTO responseDTO) {
        if (responseDTO.getStatus() == RequestStatus.APPROVED) {
            approvedResponse(responseDTO.getAccountDTO());
        }
        if (responseDTO.getStatus() == RequestStatus.DECLINED) {
            declinedResponse(responseDTO.getAccountDTO());
        }
    }

    private void approvedResponse(PlatformUserDTO student) {
        platformBot.sendMessage(student.getTelegramChatId(), "login: " + student.getLogin() + "\n" + "password: " + student.getPassword());
    }

    private void declinedResponse(PlatformUserDTO student) {
        platformBot.sendMessage(student.getTelegramChatId(), "Ваш запрос не был одобрен, повторите попытку");
    }
}

