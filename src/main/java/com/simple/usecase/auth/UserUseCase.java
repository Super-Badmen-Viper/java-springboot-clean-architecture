package com.simple.usecase.auth;

import com.simple.domain.auth.dto.UserDto;
import com.simple.domain.auth.model.User;

public interface UserUseCase {
    User singUp(UserDto userDto);

    User getUserById(String userId);

    User updateUser(UserDto userDto);

    void deleteUserById(String userId);
}
