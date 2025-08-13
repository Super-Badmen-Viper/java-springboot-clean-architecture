package com.simple.domain.auth.model;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID userId;
    private String userName;
    private String password;
    private String email;
}