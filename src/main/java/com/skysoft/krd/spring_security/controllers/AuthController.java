package com.skysoft.krd.spring_security.controllers;

import com.skysoft.krd.spring_security.dto.LoginRequestDto;
import com.skysoft.krd.spring_security.dto.LoginResponseDto;
import com.skysoft.krd.spring_security.dto.SignUpDto;
import com.skysoft.krd.spring_security.dto.UserDto;
import com.skysoft.krd.spring_security.services.AuthService;
import com.skysoft.krd.spring_security.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
  public ResponseEntity<UserDto> registerUser(@RequestBody SignUpDto signUpDto) {
            UserDto userDto= userService.signup(signUpDto);
            return ResponseEntity.ok(userDto);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                  HttpServletResponse response, HttpServletRequest request) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        Cookie cookie=new Cookie("token",loginResponseDto.getToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }
}
