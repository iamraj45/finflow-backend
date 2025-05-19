package com.app.finflow.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payment_modes")
public class PaymentMode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
