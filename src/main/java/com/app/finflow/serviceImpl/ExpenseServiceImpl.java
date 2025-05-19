package com.app.finflow.serviceImpl;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.model.Category;
import com.app.finflow.model.Expense;
import com.app.finflow.model.PaymentMode;
import com.app.finflow.model.User;
import com.app.finflow.repository.CategoryRepository;
import com.app.finflow.repository.ExpenseRepository;
import com.app.finflow.repository.PaymentModeRepository;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PaymentModeRepository paymentModeRepository;

    @Override
    public GeneralDto addExpense(ExpenseDto request) {

        GeneralDto response = new GeneralDto();
        response.setStatus(true);

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        PaymentMode paymentMode = paymentModeRepository.findById(request.getPaymentModeId()).orElseThrow(() -> new RuntimeException("Payment mode not found̵"));

        try {
            Expense expense = new Expense();
            expense.setUser(user);
            expense.setCategory(category);
            expense.setPaymentMode(paymentMode);
            expense.setAmount(request.getAmount());
            expense.setDeleted(false);
            expense.setDescription(request.getDescription());
            LocalDateTime date = Instant.ofEpochMilli(request.getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            expense.setDate(date);
            expenseRepository.save(expense);
        } catch (Exception e) {
            response.setStatus(false);
            throw new RuntimeException("Error while adding expense: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<ExpenseDto> getExpenses(Integer userId, Long startDate, Long endDate, Integer categoryId, Integer paymentModeId, Integer pageNo, Integer pageSize) throws IllegalArgumentException {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must be provided");
        }

        if (startDate > endDate) {
            throw new IllegalArgumentException("Start date must be less than end date");
        }

        LocalDateTime startDateTime = Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime endDateTime = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).toLocalDateTime();

        Pageable pageable = null;
        if (null != pageNo && null != pageSize) {
            pageable = PageRequest.of(pageNo - 1, pageSize);
        }

        Page<Expense> expensePage = null;
        List<Expense> expenses = new ArrayList<>();
        Integer pageCount;
        if (null != pageable) {
            expensePage = expenseRepository.findAllByUserIdAndStartDateAndEndDate(userId, startDateTime, endDateTime, categoryId, paymentModeId, pageable);
            pageCount = expensePage.getTotalPages();
        } else {
            pageCount = 0;
            expenses = expenseRepository.findAllByUserIdAndStartDateAndEndDateWithoutPagination(userId, startDateTime, endDateTime);
        }

        if (null != expensePage) {
            expenses = expensePage.getContent();
        }
        return expenses.stream().map(e -> new ExpenseDto(e.getId(), e.getUser().getId(), e.getAmount(), e.getDescription(), e.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), e.getCategory() != null ? e.getCategory().getId() : null, e.getPaymentMode() != null ? e.getPaymentMode().getId() : null, pageCount

        )).toList();
    }

    @Override
    public GeneralDto deleteExpense(List<Integer> expenseIds) {
        GeneralDto response = new GeneralDto();
        response.setStatus(true);
        response.setMessage("Expense deleted successfully");

        try {
            List<Expense> expenseData = expenseRepository.getExpenseById(expenseIds);
            if (null != expenseData && !expenseData.isEmpty()) {
                expenseData.forEach(dto -> {
                    dto.setDeleted(true);
                    expenseRepository.saveAndFlush(dto);
                });
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error deleting expense");
        }

        return response;
    }

    @Override
    public GeneralDto updateExpense(ExpenseDto request) {
        GeneralDto response = new GeneralDto();
        response.setStatus(true);
        response.setMessage("Expense updated successfully");

        try {
            Expense expense = expenseRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Expense not found"));
            Optional<Category> cat = categoryRepository.findById(request.getCategoryId());
            Optional<PaymentMode> mode = paymentModeRepository.findById(request.getPaymentModeId());
            expense.setAmount(request.getAmount());
            expense.setDescription(request.getDescription());
            cat.ifPresent(expense::setCategory);
            mode.ifPresent(expense::setPaymentMode);
            LocalDateTime date = Instant.ofEpochMilli(request.getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            expense.setDate(date);
            expenseRepository.saveAndFlush(expense);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error updating expense");
        }
        return response;
    }
}
