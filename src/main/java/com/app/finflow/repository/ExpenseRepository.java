package com.app.finflow.repository;

import com.app.finflow.model.Expense;
import com.app.finflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deleted = false")
    List<Expense> findAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT e FROM Expense e WHERE e.id IN (:expenseIds) AND e.deleted = false")
    List<Expense> getExpenseById(@Param("expenseIds") List<Integer> expenseId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deleted = false AND e.date BETWEEN :startDate AND :endDate")
    List<Expense> findAllByUserIdAndStartDateAndEndDate(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
