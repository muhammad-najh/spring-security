package com.skysoft.krd.spring_security.services;

import com.skysoft.krd.spring_security.entities.User;

public interface JwtService {
    String generateToken(User user);
    Long getUserIdFromToken(String username);
}