package com.app.finflow.controller;

import com.app.finflow.config.JwtUtil;
import com.app.finflow.dto.CategoryBudgetDto;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.service.BudgetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/setCategoryBudget")
    GeneralDto setCategoryBudget(@RequestParam("userId") Integer userId, @RequestBody List<CategoryBudgetDto> dto) {
        return budgetService.addCategoryBudget(userId, dto);
    }

    @DeleteMapping("/deleteCategoryBudget")
    public void deleteCategoryBudget(@RequestParam("userId") Integer userId, @RequestParam("categoryId") Integer categoryId) {
        budgetService.deleteCategoryBudget(userId, categoryId);
    }
}
