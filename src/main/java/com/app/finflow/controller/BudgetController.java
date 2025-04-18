package com.app.finflow.controller;

import com.app.finflow.config.JwtUtil;
import com.app.finflow.dto.CategoryBudgetDto;
import com.app.finflow.service.BudgetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("/getCategoryBudget")
    List<CategoryBudgetDto> getCategoryBudget(@RequestParam("userId") Integer userId) {
        return budgetService.getCategoryBudget(userId);
    }
}
