package com.simple.repository.auth;

import com.simple.domain.auth.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UUID userId);

    Optional<User> findByEmail(String email);

    void deleteById(UUID userId);

    long count();
}
