package com.app.finflow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseDto {
    private Integer id;
    private Integer userId;
    private Integer categoryId;
    private Double amount;
    private String description;
    private LocalDateTime date;
}
