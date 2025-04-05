package com.app.finflow.controller;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.model.Expense;
import com.app.finflow.model.User;
import com.app.finflow.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unsecure/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/addExpense")
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseDto request) {
        return ResponseEntity.ok(expenseService.addExpense(request));
    }

    @GetMapping("/getExpenses")
    public ResponseEntity<List<Expense>> getExpenses(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(expenseService.getExpenses(userId));
    }
}
