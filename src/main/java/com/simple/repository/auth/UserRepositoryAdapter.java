package com.simple.repository.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simple.domain.auth.entity.UserEntity;
import com.simple.domain.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserMybatisMapper userMybatisMapper;

    @Autowired
    public UserRepositoryAdapter(UserMybatisMapper userMybatisMapper) {
        this.userMybatisMapper = userMybatisMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = toEntity(user);
        userMybatisMapper.insert(userEntity);
        return toDomain(userEntity);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userMybatisMapper.selectById(userId)).map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return Optional.ofNullable(userMybatisMapper.selectOne(queryWrapper)).map(this::toDomain);
    }

    @Override
    public void deleteById(String userId) {
        userMybatisMapper.deleteById(userId);
    }

    @Override
    public long count() {
        return userMybatisMapper.selectCount(null);
    }

    private User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userEntity.getId());
        user.setUserName(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        return user;
    }

    private UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return UserEntity.builder()
                .id(user.getUserId())
                .username(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .build();
    }
}