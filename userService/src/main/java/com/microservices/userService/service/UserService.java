package com.microservices.userService.service;

import com.microservices.userService.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);


}
