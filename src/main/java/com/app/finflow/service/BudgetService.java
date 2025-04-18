package com.app.finflow.service;

import com.app.finflow.dto.CategoryBudgetDto;
import com.app.finflow.dto.GeneralDto;

import java.util.List;

public interface BudgetService {
    List<CategoryBudgetDto> getCategoryBudget(Integer userId);

    GeneralDto addCategoryBudget(Integer userId, List<CategoryBudgetDto> dto);
}
