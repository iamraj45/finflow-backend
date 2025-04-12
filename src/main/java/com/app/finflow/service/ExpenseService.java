package com.app.finflow.service;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.dto.GeneralDto;

import java.util.List;

public interface ExpenseService {

    GeneralDto addExpense(ExpenseDto request);

    List<ExpenseDto> getExpenses(Integer userId);

    GeneralDto deleteExpense(List<Integer> expenseId);
}
