package com.app.finflow.controller;

import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.UserDto;
import com.app.finflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserData")
    UserDto getUserData(@RequestParam("userId") Integer userId) {
        return userService.getUserData(userId);
    }

    @PostMapping("/updateUserData")
    GeneralDto setUserData(@RequestBody UserDto request) {
        return userService.setUserData(request);
    }
}
