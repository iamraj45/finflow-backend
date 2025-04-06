package com.app.finflow.service;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.model.Expense;

import java.util.List;

public interface ExpenseService {

    Expense addExpense(ExpenseDto request);

    List<ExpenseDto> getExpenses(Integer userId);
}
