package com.news.service;

import com.news.entity.User;
import com.news.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);

    User createUser(UserDto user);

    void signUp(UserDto userDto);

    User getUserById(Long id);

    User updateUser(Long id, UserDto userDto);

    List<User> getAllUsers();
}
