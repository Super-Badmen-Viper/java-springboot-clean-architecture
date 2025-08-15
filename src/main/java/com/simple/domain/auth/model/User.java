package com.simple.domain.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String role;
    private java.util.Date createTime;
    private java.util.Date updateTime;
}