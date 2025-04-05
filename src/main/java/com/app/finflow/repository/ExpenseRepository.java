package com.app.finflow.repository;

import com.app.finflow.model.Expense;
import com.app.finflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId")
    List<Expense> findAllByUserId(@Param("userId") Integer userId);
}
