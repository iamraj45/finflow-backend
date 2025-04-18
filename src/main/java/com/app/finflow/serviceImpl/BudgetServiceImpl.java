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
            budgetData.setCategoryName(dto.getCategory().getName());
            budgetData.setBudget(dto.getBudget());

            response.add(budgetData);
        });
        return response;
    }

    @Override
    public GeneralDto addCategoryBudget(Integer userId, List<CategoryBudgetDto> dtoList) {
        GeneralDto response = new GeneralDto();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setStatus(false);
            response.setMessage("User not found");
            return response;
        }

        for (CategoryBudgetDto dto : dtoList) {
            Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            if (category == null) continue;

            CategoryBudget existing = budgetRepository.findByUserAndCategory(user, category);
            if (existing != null) {
                // Update existing budget
                existing.setBudget(dto.getBudget());
                budgetRepository.saveAndFlush(existing);
            } else {
                // Insert new budget
                CategoryBudget newBudget = new CategoryBudget();
                newBudget.setUser(user);
                newBudget.setCategory(category);
                newBudget.setBudget(dto.getBudget());
                budgetRepository.save(newBudget);
            }
        }

        response.setStatus(true);
        response.setMessage("Budget set successfully");
        return response;
    }

    @Override
    public void deleteCategoryBudget(Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        CategoryBudget existing = budgetRepository.findByUserAndCategory(user, category);
        if (existing != null) {
            budgetRepository.delete(existing);
        } else {
            throw new IllegalArgumentException("CategoryBudget not found for the given user and category");
        }
    }
}
