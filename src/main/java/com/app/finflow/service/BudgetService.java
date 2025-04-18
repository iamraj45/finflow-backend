package com.app.finflow.service;

import com.app.finflow.dto.CategoryBudgetDto;

import java.util.List;

public interface BudgetService {
    List<CategoryBudgetDto> getCategoryBudget(Integer userId);
}
