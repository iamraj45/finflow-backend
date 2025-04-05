package com.app.finflow.repository;

import com.app.finflow.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("Select e from Expense e where e.user.id = :userId")
    List<Expense> findByUserId(@Param("userId") Integer userId);
}
