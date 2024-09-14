package com.skysoft.krd.spring_security.services;

import com.skysoft.krd.spring_security.dto.LoginRequestDto;
import com.skysoft.krd.spring_security.dto.LoginResponseDto;
import com.skysoft.krd.spring_security.dto.SignUpDto;
import com.skysoft.krd.spring_security.dto.UserDto;
import com.skysoft.krd.spring_security.entities.User;
import com.skysoft.krd.spring_security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDto signup(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("Email already in use");
        }

        User userTobeSaved=modelMapper.map(signUpDto, User.class);
        userTobeSaved.setPassword(passwordEncoder.encode(userTobeSaved.getPassword()));
        User savedUser=userRepository.save(userTobeSaved);
        return modelMapper.map(savedUser, UserDto.class);
    }


    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
