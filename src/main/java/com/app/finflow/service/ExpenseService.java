package com.app.finflow.service;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.dto.GeneralDto;

import java.util.List;

public interface ExpenseService {

    GeneralDto addExpense(ExpenseDto request);

    List<ExpenseDto> getExpenses(Integer userId, Long startDate, Long endDate, Integer categoryId, Integer paymentModeId, Integer pageNo, Integer pageSize) throws IllegalArgumentException;

    GeneralDto deleteExpense(List<Integer> expenseId);

    GeneralDto updateExpense(ExpenseDto request);
}
