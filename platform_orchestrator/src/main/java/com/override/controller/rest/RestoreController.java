package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import com.override.feign.TelegramBotFeign;
import com.override.model.PlatformUser;
import com.override.repository.PlatformUserRepository;
import com.override.service.VerificationService;
import dto.ChangePasswordDTO;
import dto.MessageDTO;
import dto.PlatformUserDTO;
import dto.ResponseJoinRequestDTO;
import enums.Communication;
import enums.RequestStatus;
import io.swagger.annotations.ApiOperation;
import liquibase.pro.packaged.O;
import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

@Controller
public class RestoreController {

    String code;

    HashMap <String, ChangePasswordDTO> changePasswordDTOHashMap = new HashMap<>();

    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationService verificationService;

    @RequestMapping(value = "/restore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String restore(@RequestBody ChangePasswordDTO changePasswordDTO){
        if (changePasswordDTOHashMap.containsKey(changePasswordDTO.getUsername())
        && changePasswordDTOHashMap.get(changePasswordDTO.getUsername()).getCode().equals(changePasswordDTO.getCode())){
            PlatformUser platformUser = platformUserRepository.findFirstByLogin(changePasswordDTO.getUsername());
            platformUser.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
            platformUserRepository.save(platformUser);
            changePasswordDTOHashMap.remove(changePasswordDTO.getUsername());
        }
        return "redirect:/login";
    }

    @GetMapping("/getCode/{username}")
    public ResponseEntity<PlatformUserDTO> getCode(@PathVariable("username") String username){
        PlatformUser platformUser =  platformUserRepository.findFirstByLogin(username);
        Random random = new Random();
        code = Integer.toString(random.nextInt(9999));
        notificatorFeign.sendMessage(username, code, Communication.TELEGRAM);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setUsername(username);
        changePasswordDTO.setCode(code);
        changePasswordDTOHashMap.put(username, changePasswordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
