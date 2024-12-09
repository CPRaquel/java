package com.rcaspe.CarRegistry.service.impl;

import com.rcaspe.CarRegistry.controller.dtos.JwtResponse;
import com.rcaspe.CarRegistry.controller.dtos.LogInRequest;
import com.rcaspe.CarRegistry.controller.dtos.SignUpRequest;
import com.rcaspe.CarRegistry.entity.UserEntity;
import com.rcaspe.CarRegistry.repository.UserRepository;
import com.rcaspe.CarRegistry.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signup(SignUpRequest request) throws Exception{
        var user = UserEntity
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CLIENT")
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);

        return JwtResponse.builder().token(jwt).build();
    }

    @Override
    public JwtResponse login(LogInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        return JwtResponse.builder().token(jwt).build();
    }
}
