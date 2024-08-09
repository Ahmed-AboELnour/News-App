package com.news.service.serviceImp;

import com.news.entity.News;
import com.news.entity.User;
import com.news.exception.ResourceNotFoundException;
import com.news.repository.UserRepository;
import com.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        User existinguser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existinguser.setEmail(user.getEmail() == null ?existinguser.getEmail() : user.getEmail());
        existinguser.setFullName(user.getFullName() == null ?existinguser.getFullName() : user.getFullName());
        existinguser.setPassword(user.getPassword() == null ?existinguser.getPassword() : user.getPassword());
        existinguser.setRole(user.getRole() == null ?existinguser.getRole() : user.getRole());
        existinguser.setDateOfBirth(user.getDateOfBirth() == null ?existinguser.getDateOfBirth() : user.getDateOfBirth());
        return userRepository.save(existinguser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}