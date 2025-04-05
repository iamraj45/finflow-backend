package com.app.finflow.service;

import com.app.finflow.dto.ExpensesDto;
import com.app.finflow.model.Expenses;

import java.util.List;

public interface ExpensesService {

    Expenses addExpense(ExpensesDto request);

    List<Expenses> getExpenses(Integer userId);
}
