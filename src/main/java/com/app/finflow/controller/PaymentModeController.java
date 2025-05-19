package com.app.finflow.controller;

import com.app.finflow.dto.PaymentModeDto;
import com.app.finflow.service.PaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentModeController {

    @Autowired
    PaymentModeService paymentModeService;

    @GetMapping("/getAllPaymentModes")
    List<PaymentModeDto> get(){
        return paymentModeService.getAllPaymentModes();
    }
}
