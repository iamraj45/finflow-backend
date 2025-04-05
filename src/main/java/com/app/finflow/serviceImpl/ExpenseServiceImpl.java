package com.app.finflow.serviceImpl;

import com.app.finflow.dto.ExpenseDto;
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
    public Expense addExpense(ExpenseDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());

        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getExpenses(Integer userId) {
        return expenseRepository.findAllByUserId(userId);
    }
}
