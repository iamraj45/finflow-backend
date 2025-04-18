package com.app.finflow.controller;

import com.app.finflow.dto.ExpenseDto;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.model.Expense;
import com.app.finflow.model.User;
import com.app.finflow.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/addExpense")
    public ResponseEntity<GeneralDto> addExpense(@RequestBody ExpenseDto request) {
        return ResponseEntity.ok(expenseService.addExpense(request));
    }

    @GetMapping("/getExpenses")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@RequestParam("userId") Integer userId, @RequestParam Long startDate, @RequestParam Long endDate) {
        return ResponseEntity.ok(expenseService.getExpenses(userId, startDate, endDate));
    }

    @PostMapping("/deleteExpense")
    public ResponseEntity<GeneralDto> deleteExpense(@RequestParam("expenseId") List<Integer> expenseId) {
        return ResponseEntity.ok(expenseService.deleteExpense(expenseId));
    }

    @PostMapping("/updateExpense")
    public ResponseEntity<GeneralDto> updateExpense(@RequestBody ExpenseDto request) {
        return ResponseEntity.ok(expenseService.updateExpense(request));
    }
}
