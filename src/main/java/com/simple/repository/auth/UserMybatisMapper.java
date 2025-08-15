package com.simple.repository.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.domain.auth.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMybatisMapper extends BaseMapper<UserEntity> {
}