package com.varsemp.authentication.service.impl;

import com.varsemp.authentication.dto.AuthResponse;
import com.varsemp.authentication.dto.LoginRequest;
import com.varsemp.authentication.dto.RegisterRequest;
import com.varsemp.authentication.entity.User;
import com.varsemp.authentication.repository.UserRepo;
import com.varsemp.authentication.service.AuthService;
import com.varsemp.authentication.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request){

        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request){

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
