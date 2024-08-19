package com.skysoft.krd.spring_security.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String name;
}
