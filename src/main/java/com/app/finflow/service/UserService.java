package com.app.finflow.service;


import com.app.finflow.dto.ExpensesDto;
import com.app.finflow.dto.UserDto;

import java.util.List;


public interface UserService {

    List<UserDto> getUserData();
}
