package com.app.finflow.repository;

import com.app.finflow.model.Expense;
import com.app.finflow.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>, PagingAndSortingRepository<Expense, Integer> {
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deleted = false")
    List<Expense> findAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT e FROM Expense e WHERE e.id IN (:expenseIds) AND e.deleted = false")
    List<Expense> getExpenseById(@Param("expenseIds") List<Integer> expenseId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deleted = false AND e.date BETWEEN :startDate AND :endDate" +
            " AND (:categoryId IS NULL OR e.category.id = :categoryId) ORDER BY e.date DESC")
    Page<Expense> findAllByUserIdAndStartDateAndEndDate(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                                        @Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.deleted = false AND e.date BETWEEN :startDate AND :endDate ORDER BY e.date DESC")
    List<Expense> findAllByUserIdAndStartDateAndEndDateWithoutPagination(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
