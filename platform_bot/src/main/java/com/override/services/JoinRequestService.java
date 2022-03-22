package com.override.services;

import com.override.PlatformBot;
import dtos.PlatformUserDTO;
import dtos.ResponseJoinRequestDTO;
import enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinRequestService {

    @Autowired
    private PlatformBot platformBot;

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

