package com.r2s.user.dto;

import com.r2s.user.entity.User;

public class UserResponse {
    private String username;
    private String role;
    private String email;
    private String fullName;

    public static UserResponse fromEntity(User u) {
        var r = new UserResponse();
        r.username = u.getUsername();
        r.role = u.getRole().name();
        r.email = u.getEmail();
        r.fullName = u.getFullName();
        return r;
    }

    // getters/setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}
