package com.simple.repository.auth;

import com.simple.domain.auth.model.User;

import java.util.Optional;

import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    void deleteById(String userId);

    long count();
}
