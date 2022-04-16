package com.override.services;

import com.override.PlatformBot;
import com.override.feigns.NotificatorFeign;
import dtos.CommonErrorDTO;
import dtos.PlatformUserDTO;
import dtos.ResponseJoinRequestDTO;
import enums.Communications;
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
        notificatorFeign.sendMessage(student.getLogin(), "login: " + student.getLogin() + "\n" + "password: " + student.getPassword(), Communications.TELEGRAM);
    }

    private void declinedResponse(PlatformUserDTO student) {
        notificatorFeign.sendMessage(student.getLogin(), "Ваш запрос не был одобрен, повторите попытку", Communications.TELEGRAM);
    }
}

