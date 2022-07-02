package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.PaymentService;
import dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> saveOrUpdatePayment(@RequestBody PaymentDTO paymentDTO,
                                                      @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        paymentService.saveOrUpdatePayment(paymentDTO, user.getUsername());
        return new ResponseEntity<>("Счет на оплату добавлен!", HttpStatus.OK);
    }
}