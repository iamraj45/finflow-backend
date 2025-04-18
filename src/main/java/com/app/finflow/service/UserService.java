package com.app.finflow.service;


import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto getUserData(Integer userId);

    GeneralDto setUserData(UserDto request);
}
