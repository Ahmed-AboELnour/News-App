package com.news.service;

import com.news.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);

    User createUser(User user);

    User getUserById(Long id);

    User updateUser(Long id, User user);

    List<User> getAllUsers();
}
