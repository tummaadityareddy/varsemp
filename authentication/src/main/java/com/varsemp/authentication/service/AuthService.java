package com.varsemp.authentication.service;

import com.varsemp.authentication.dto.AuthResponse;
import com.varsemp.authentication.dto.LoginRequest;
import com.varsemp.authentication.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
