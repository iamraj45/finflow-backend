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
    private Long date;
    private Integer totalPage;

    public ExpenseDto(Integer id, Integer userId, Double amount, String description, Long date, Integer categoryId, Integer pageCount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
        this.totalPage = pageCount;
    }
}
