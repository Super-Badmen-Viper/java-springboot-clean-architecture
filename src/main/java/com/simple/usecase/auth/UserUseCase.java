package com.simple.usecase.auth;

import com.simple.domain.auth.dto.UserDto;
import com.simple.domain.auth.model.User;

import java.util.UUID;

public interface UserUseCase {
    User singUp(UserDto userDto);

    User getUserById(UUID userId);

    User updateUser(UserDto userDto);

    void deleteUserById(UUID userId);
}
