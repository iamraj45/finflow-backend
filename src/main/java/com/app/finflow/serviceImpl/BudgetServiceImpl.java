package com.app.finflow.serviceImpl;

import com.app.finflow.dto.CategoryBudgetDto;
import com.app.finflow.model.CategoryBudget;
import com.app.finflow.repository.BudgetRepository;
import com.app.finflow.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    @Override
    public List<CategoryBudgetDto> getCategoryBudget(Integer userId) {
        List<CategoryBudgetDto> response = new ArrayList<>();

        List<CategoryBudget> budgetList = budgetRepository.findByUserId(userId);
        budgetList.forEach(dto -> {
            CategoryBudgetDto budgetData = new CategoryBudgetDto();
            budgetData.setCategoryId(dto.getCategory().getId());
            budgetData.setBudget(dto.getBudget());

            response.add(budgetData);
        });
        return response;

    }
}
