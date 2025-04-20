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
    public ResponseEntity<List<ExpenseDto>> getExpenses(@RequestParam("userId") Integer userId, @RequestParam("startDate") Long startDate, @RequestParam("endDate") Long endDate,
                                                        @RequestParam(value = "categoryId", required = false) Integer categoryId, @RequestParam(value = "pageNo", required = false)Integer pageNo,
                                                        @RequestParam(value = "pageSize", required = false)Integer pageSize) throws IllegalArgumentException {
        return ResponseEntity.ok(expenseService.getExpenses(userId, startDate, endDate, categoryId, pageNo, pageSize));
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
