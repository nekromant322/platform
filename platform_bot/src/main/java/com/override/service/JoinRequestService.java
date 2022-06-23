package com.override.service;

import com.override.PlatformBot;
import com.override.feign.NotificatorFeign;
import dto.PlatformUserDTO;
import dto.ResponseJoinRequestDTO;
import enums.Communication;
import enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinRequestService {

    @Autowired
    private PlatformBot platformBot;

    @Autowired
    private NotificatorFeign notificatorFeign;

    public void processJoinRequestResponse(ResponseJoinRequestDTO responseDTO) {
        if (responseDTO.getStatus() == RequestStatus.APPROVED) {
            approvedResponse(responseDTO.getAccountDTO());
        }
        if (responseDTO.getStatus() == RequestStatus.DECLINED) {
            declinedResponse(responseDTO.getAccountDTO());
        }
    }

    private void approvedResponse(PlatformUserDTO student) {
        notificatorFeign.sendMessage(student.getLogin(), "login: " + student.getLogin() + "\n" + "password: " + student.getPassword(), Communication.TELEGRAM);
    }

    private void declinedResponse(PlatformUserDTO student) {
        notificatorFeign.sendMessage(student.getLogin(), "Ваш запрос не был одобрен, повторите попытку", Communication.TELEGRAM);
    }
}

