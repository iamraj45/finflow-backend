package com.app.finflow.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.ToString;

@Entity
@Data
@Table(name = "users")
@ToString(exclude = {"expense", "categoryBudgets"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expense;

    private Double totalBudget;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CategoryBudget> categoryBudgets;

    @Column(name = "login_method")
    private String loginMethod; // values: "google", "email"

    private boolean verified;
    private String verificationToken;
    private LocalDateTime tokenExpiry;
}
