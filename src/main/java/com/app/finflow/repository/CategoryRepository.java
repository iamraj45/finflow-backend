package com.app.finflow.repository;

import com.app.finflow.model.Category;
import com.app.finflow.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
