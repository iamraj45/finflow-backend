package com.app.finflow.service;

import com.app.finflow.dto.PaymentModeDto;

import java.util.List;

public interface PaymentModeService {
    List<PaymentModeDto> getAllPaymentModes();
}
