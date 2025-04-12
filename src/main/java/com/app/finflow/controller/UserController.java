package com.app.finflow.controller;

import com.app.finflow.dto.UserDto;
import com.app.finflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserData")
    List<UserDto> getUserData(){
        return userService.getUserData();
    }
}
