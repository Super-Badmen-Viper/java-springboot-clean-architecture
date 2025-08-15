package com.simple.service.auth;

import com.simple.domain.auth.dto.UserDto;
import com.simple.domain.auth.model.User;
import com.simple.repository.auth.UserRepository;
import com.simple.usecase.auth.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User singUp(UserDto userDto) {
        User user = new User();
        String userId = userDto.getUserId();
        if (userId.isEmpty()) {
            userId = UUID.randomUUID().toString();
        }
        user.setUserId(userId);
        user.setUserName(userDto.getUserName());
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encryptedPassword);
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with id " + userId + " not found.")
        );
    }

    @Override
    public User updateUser(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with id: " + userDto.getUserId())
                );

        if (userDto.getUserName() != null) {
            existingUser.setUserName(userDto.getUserName());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
            existingUser.setPassword(encryptedPassword);
        }

        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
}
