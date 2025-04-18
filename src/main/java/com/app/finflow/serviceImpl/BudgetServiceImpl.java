package com.app.finflow.serviceImpl;

import com.app.finflow.dto.CategoryBudgetDto;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.model.Category;
import com.app.finflow.model.CategoryBudget;
import com.app.finflow.model.User;
import com.app.finflow.repository.BudgetRepository;
import com.app.finflow.repository.CategoryRepository;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

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

    @Override
    public GeneralDto addCategoryBudget(Integer userId, List<CategoryBudgetDto> dto) {
        GeneralDto response = new GeneralDto();
        List<CategoryBudget> budgetList = new ArrayList<>();

        dto.forEach(budgetData -> {
            CategoryBudget budget = new CategoryBudget();
            User user = userRepository.findById(userId).orElse(null);
            Category category = categoryRepository.findById(budgetData.getCategoryId()).orElse(null);
            budget.setUser(user);
            budget.setCategory(category);
            budget.setBudget(budgetData.getBudget());

            budgetList.add(budget);
        });

        budgetRepository.saveAll(budgetList);
        response.setMessage("Budget set successfully");
        return response;
    }
}
