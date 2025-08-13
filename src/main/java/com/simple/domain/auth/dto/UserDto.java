package com.simple.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 12, message = "密码长度需在6-12之间")
    private String password;

    @Email(message = "email格式不正确")
    private String email;
}