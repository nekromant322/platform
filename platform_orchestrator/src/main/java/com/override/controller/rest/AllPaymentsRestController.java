package com.override.controller.rest;

import com.override.model.Payment;
import com.override.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/allPayments")
public class AllPaymentsRestController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getAllPayments")
    private List<Payment> getAllPayments(){
        return paymentService.getAllPayment();
    }
}
