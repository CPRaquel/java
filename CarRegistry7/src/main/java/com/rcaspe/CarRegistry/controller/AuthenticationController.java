package com.rcaspe.CarRegistry.controller;

import com.rcaspe.CarRegistry.controller.dtos.JwtResponse;
import com.rcaspe.CarRegistry.controller.dtos.LogInRequest;
import com.rcaspe.CarRegistry.controller.dtos.SignUpRequest;
import com.rcaspe.CarRegistry.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signup(@RequestBody LogInRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
