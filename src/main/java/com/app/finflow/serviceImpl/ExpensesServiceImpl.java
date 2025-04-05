package com.app.finflow.serviceImpl;

import com.app.finflow.dto.ExpensesDto;
import com.app.finflow.model.Category;
import com.app.finflow.model.Expenses;
import com.app.finflow.model.User;
import com.app.finflow.repository.CategoryRepository;
import com.app.finflow.repository.ExpensesRepository;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    ExpensesRepository expensesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Expenses addExpense(ExpensesDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expenses expense = new Expenses();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());

        return expensesRepository.save(expense);
    }

    @Override
    public List<Expenses> getExpenses(Integer userId) {
        return expensesRepository.findByUserId(userId);
    }
}
