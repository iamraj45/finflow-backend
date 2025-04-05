package com.app.finflow.controller;

import com.app.finflow.dto.ExpensesDto;
import com.app.finflow.model.Expenses;
import com.app.finflow.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unsecure/expenses")
public class ExpensesController {

    @Autowired
    ExpensesService expensesService;

    @PostMapping("/addExpense")
    public ResponseEntity<Expenses> addExpense(@RequestBody ExpensesDto request) {
        return ResponseEntity.ok(expensesService.addExpense(request));
    }

    @GetMapping("/getExpenses")
    public ResponseEntity<List<Expenses>> getExpenses(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(expensesService.getExpenses(userId));
    }
}
