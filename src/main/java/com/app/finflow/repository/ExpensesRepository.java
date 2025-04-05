package com.app.finflow.repository;

import com.app.finflow.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {
    @Query("Select e from Expenses e where e.user.id = :userId")
    List<Expenses> findByUserId(@Param("userId") Integer userId);
}
