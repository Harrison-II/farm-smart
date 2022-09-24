package com.project.farmsmart.service;

import com.project.farmsmart.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUserById(Long id);
}
