package com.app.finflow.controller;

import com.app.finflow.dto.CategoryDto;
import com.app.finflow.dto.UserDto;
import com.app.finflow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/unsecure")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAllCategories")
    List<CategoryDto> get(){
        return categoryService.getAllCategories();
    }
}
