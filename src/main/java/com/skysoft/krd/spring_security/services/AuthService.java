package com.skysoft.krd.spring_security.services;

import com.skysoft.krd.spring_security.dto.LoginRequestDto;
import com.skysoft.krd.spring_security.dto.LoginResponseDto;
import com.skysoft.krd.spring_security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticated=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        User user=(User)authenticated.getPrincipal();
        String token = jwtService.generateToken(user);

        return LoginResponseDto.builder().token(token).build();


    }
}
