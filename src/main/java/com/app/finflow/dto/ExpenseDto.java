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

    public ExpenseDto(Integer id, Integer userId, Double amount, String description, LocalDateTime date, Integer categoryId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
    }
}
