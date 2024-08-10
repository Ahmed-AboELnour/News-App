package com.news.service.serviceImp;

import com.news.entity.User;
import com.news.exception.ResourceNotFoundException;
import com.news.model.UserDto;
import com.news.repository.UserRepository;
import com.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceNotFoundException("User with email " + userDto.getEmail() + " already exists.");
        }
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setRole(userDto.getRole());
        return userRepository.save(user);
    }

    @Override
    public void signUp(UserDto userDto) {
        createUser(userDto);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, UserDto userDto) {
        User existinguser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existinguser.setEmail(userDto.getEmail() == null ?existinguser.getEmail() : userDto.getEmail());
        existinguser.setFullName(userDto.getFullName() == null ?existinguser.getFullName() : userDto.getFullName());
        existinguser.setPassword(userDto.getPassword() == null ? passwordEncoder.encode(existinguser.getPassword()) : passwordEncoder.encode(userDto.getPassword()));
        existinguser.setRole(userDto.getRole() == null ?existinguser.getRole() : userDto.getRole());
        existinguser.setDateOfBirth(userDto.getDateOfBirth() == null ?existinguser.getDateOfBirth() : userDto.getDateOfBirth());
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