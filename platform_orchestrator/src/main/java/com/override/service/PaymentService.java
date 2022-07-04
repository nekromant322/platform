package com.override.service;

import com.override.mapper.PaymentMapper;
import com.override.model.Payment;
import com.override.repository.PaymentRepository;
import dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    public void saveOrUpdatePayment(PaymentDTO paymentDTO, String username) {
        paymentRepository.save(paymentMapper.dtoToEntity(paymentDTO,username));
    }

    public void save(Payment payment){
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }
}
