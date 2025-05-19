package com.app.finflow.serviceImpl;

import com.app.finflow.dto.PaymentModeDto;
import com.app.finflow.model.PaymentMode;
import com.app.finflow.repository.PaymentModeRepository;
import com.app.finflow.service.PaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentModeServiceImpl implements PaymentModeService {

    @Autowired
    PaymentModeRepository paymentModeRepository;

    @Override
    public List<PaymentModeDto> getAllPaymentModes() {
        List<PaymentModeDto> response = new ArrayList<>();
        List<PaymentMode> paymentModes = paymentModeRepository.getAllPaymentModes();
        paymentModes.forEach(dto -> {
            PaymentModeDto paymentModeDto = new PaymentModeDto();
            paymentModeDto.setId(dto.getId());
            paymentModeDto.setName(dto.getName());

            response.add(paymentModeDto);
        });
        return response;
    }
}
