package com.rcaspe.CarRegistry.service;

import com.rcaspe.CarRegistry.controller.dtos.JwtResponse;
import com.rcaspe.CarRegistry.controller.dtos.LogInRequest;
import com.rcaspe.CarRegistry.controller.dtos.SignUpRequest;

public interface AuthenticationService {

    public JwtResponse signup(SignUpRequest request) throws Exception;

    public JwtResponse login(LogInRequest request);

}
