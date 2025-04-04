package com.app.finflow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}
