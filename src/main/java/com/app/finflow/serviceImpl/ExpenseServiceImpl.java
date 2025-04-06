package com.app.finflow.serviceImpl;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.model.Category;
import com.app.finflow.model.Expense;
import com.app.finflow.model.User;
import com.app.finflow.repository.CategoryRepository;
import com.app.finflow.repository.ExpenseRepository;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public GeneralDto addExpense(ExpenseDto request) {

        GeneralDto response = new GeneralDto();
        response.setStatus(true);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        try {
            Expense expense = new Expense();
            expense.setUser(user);
            expense.setCategory(category);
            expense.setAmount(request.getAmount());
            expense.setDescription(request.getDescription());
            expense.setDate(request.getDate());
            expenseRepository.save(expense);
        } catch (Exception e) {
            response.setStatus(false);
            throw new RuntimeException("Error while adding expense: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<ExpenseDto> getExpenses(Integer userId) {
        List<Expense> expenses = expenseRepository.findAllByUserId(userId);

        return expenses.stream()
                .map(e -> new ExpenseDto(
                        e.getId(),
                        e.getUser().getId(),
                        e.getAmount(),
                        e.getDescription(),
                        e.getDate(),
                        e.getCategory() != null ? e.getCategory().getId() : null
                ))
                .toList();
    }
}
