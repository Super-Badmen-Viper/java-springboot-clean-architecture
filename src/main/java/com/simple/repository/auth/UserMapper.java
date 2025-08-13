package com.simple.repository.auth;

import com.simple.domain.auth.model.User;
import com.simple.domain.auth.entity.UserJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserJpaEntity userJpaEntity);

    UserJpaEntity toEntity(User user);
}
