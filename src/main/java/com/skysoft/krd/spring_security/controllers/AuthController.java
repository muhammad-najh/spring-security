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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
  public ResponseEntity<UserDto> registerUser(@RequestBody SignUpDto signUpDto) {
            UserDto userDto= userService.signup(signUpDto);
            return ResponseEntity.ok(userDto);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                  HttpServletResponse response, HttpServletRequest request) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        Cookie cookie=new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly("PROD".equals(deployEnv)); //if its production then we will keep this cookie only in HTTPS

        cookie.setSecure(true); //now this cookie can be pass only in HTTPS
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refreshToken=Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh Token not found inside cookies"));

        LoginResponseDto loginResponseDto=authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDto);
    }
}
