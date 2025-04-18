package com.app.finflow.repository;

import com.app.finflow.model.Category;
import com.app.finflow.model.CategoryBudget;
import com.app.finflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<CategoryBudget, Integer> {

    @Query("Select cb from CategoryBudget cb where cb.user.id = :userId")
    List<CategoryBudget> findByUserId(@Param("userId") Integer userId);

    CategoryBudget findByUserAndCategory(User user, Category category);
}
