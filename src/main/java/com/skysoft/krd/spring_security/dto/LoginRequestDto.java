package com.skysoft.krd.spring_security.dto;


import lombok.Data;

@Data
public class LoginRequestDto {

    private String email;
    private String password;
}
