package com.app.finflow.serviceImpl;

import com.app.finflow.dto.UserDto;
import com.app.finflow.model.User;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto getUserData(Integer userId) {
        UserDto userData = new UserDto();
        Optional<User> userDetail = userRepository.findById(userId);

        if(userDetail.isPresent()) {
            userData.setId(userDetail.get().getId());
            userData.setName(userDetail.get().getName());
            userData.setEmail(userDetail.get().getEmail());
            userData.setTotalBudget(userDetail.get().getTotalBudget());
        }
        return userData;
    }
}
