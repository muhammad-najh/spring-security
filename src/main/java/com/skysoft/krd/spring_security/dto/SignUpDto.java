package com.skysoft.krd.spring_security.dto;

import com.skysoft.krd.spring_security.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
