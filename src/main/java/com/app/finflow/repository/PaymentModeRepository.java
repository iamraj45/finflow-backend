package com.app.finflow.repository;

import com.app.finflow.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode, Integer> {

    @Query("select p from PaymentMode p")
    List<PaymentMode> getAllPaymentModes();
}
