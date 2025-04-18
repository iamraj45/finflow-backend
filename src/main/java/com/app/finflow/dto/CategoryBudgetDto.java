package com.app.finflow.dto;

import lombok.Data;

@Data
public class CategoryBudgetDto {
    private Integer categoryId;
    private String categoryName;
    private Double budget;
}
