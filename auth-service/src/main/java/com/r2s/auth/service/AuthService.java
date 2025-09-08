package com.r2s.auth.service;

import com.r2s.auth.dto.*;
import com.r2s.auth.entity.Role;
import com.r2s.auth.entity.User;
import com.r2s.auth.repository.UserRepository;
import com.r2s.auth.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@Service
//public class AuthService {
//
//    private final UserRepository userRepo;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//    private final AuthenticationManager authManager;
//
//    public AuthService(UserRepository userRepo,
//                       PasswordEncoder passwordEncoder,
//                       JwtUtil jwtUtil,
//                       AuthenticationManager authManager) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//        this.authManager = authManager;
//    }
//
//    public void register(RegisterRequest request) {
//        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
//            throw new RuntimeException("Username exists");
//        }
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole("USER");
//        userRepo.save(user);
//    }
//
//    public AuthResponse login(LoginRequest request) {
//
//
//        User user = userRepo.findByUsername(request.getUsername())
//         .orElseThrow(() -> new UsernameNotFoundException("Not found"));
//
//         if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
//             throw new BadCredentialsException("Invalid password");
//
//         String token = jwtUtil.generateToken(user.getUsername());
//         return new AuthResponse(token);
//    }
//}


import com.r2s.auth.dto.AuthResponse;
import com.r2s.auth.dto.LoginRequest;
import com.r2s.auth.dto.RegisterRequest;
import com.r2s.auth.entity.User;
import com.r2s.auth.repository.UserRepository;
import com.r2s.auth.security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt
        user.setRole(request.getRole() != null ? request.getRole() : Role.ROLE_USER);
        userRepo.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}

