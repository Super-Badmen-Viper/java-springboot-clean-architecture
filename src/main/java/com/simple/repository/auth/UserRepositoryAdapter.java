package com.simple.repository.auth;

import com.simple.domain.auth.model.User;
import com.simple.domain.auth.entity.UserJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaInterface userJpaInterface;
    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryAdapter(UserJpaInterface userJpaInterface, UserMapper userMapper) {
        this.userJpaInterface = userJpaInterface;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserJpaEntity userJpaEntity = userMapper.toEntity(user);
        UserJpaEntity savedEntity = userJpaInterface.save(userJpaEntity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userJpaInterface.findById(userId).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaInterface.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public void deleteById(UUID userId) {
        userJpaInterface.deleteById(userId);
    }

    @Override
    public long count() {
        return userJpaInterface.count();
    }
}