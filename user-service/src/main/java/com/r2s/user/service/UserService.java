package com.r2s.user.service;

import com.r2s.user.dto.*;
import com.r2s.user.entity.*;
import com.r2s.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) { this.repo = repo; }

    public List<UserResponse> getAllUsers() {
        return repo.findAll().stream().map(UserResponse::fromEntity).collect(Collectors.toList());
    }

    public UserResponse getUserByUsername(String username) {
        var u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return UserResponse.fromEntity(u);
    }



    public void deleteUser(String username) { repo.deleteByUsername(username); }


    public UserResponse ensureProfile(String username, String roleFromToken) {
        var u = repo.findByUsername(username).orElseGet(() -> {
            var nu = new User();
            nu.setUsername(username);
            nu.setRole(Role.valueOf(roleFromToken)); // tin role từ token
            return repo.save(nu);
        });
        return UserResponse.fromEntity(u);
    }


}
