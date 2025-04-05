package com.app.finflow.serviceImpl;

import com.app.finflow.dto.UserDto;
import com.app.finflow.model.User;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDto> getUserData() {
        List<UserDto> userDataResponse = new ArrayList<>();
        List<User> userDataList = userRepository.findAllUser();
        userDataList.forEach(dto -> {
            UserDto userData = new UserDto();
            userData.setId(dto.getId());
            userData.setName(dto.getName());
            userData.setEmail(dto.getEmail());
            userData.setPassword(dto.getPassword());

            userDataResponse.add(userData);
        });
        return userDataResponse;
    }
}
