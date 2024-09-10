package com.skysoft.krd.spring_security.services;

import com.skysoft.krd.spring_security.entities.User;

public interface JwtService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    Long getUserIdFromToken(String username);
}