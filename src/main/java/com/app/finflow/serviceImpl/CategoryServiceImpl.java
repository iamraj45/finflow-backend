package com.app.finflow.serviceImpl;

import com.app.finflow.dto.CategoryDto;
import com.app.finflow.model.Category;
import com.app.finflow.repository.CategoryRepository;
import com.app.finflow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {

        List<CategoryDto> categoryDataResponse = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllCategories();
        categories.forEach(dto -> {
            CategoryDto categoryData = new CategoryDto();
            categoryData.setId(dto.getId());
            categoryData.setName(dto.getName());

            categoryDataResponse.add(categoryData);
        });
        return categoryDataResponse;
    }
}
