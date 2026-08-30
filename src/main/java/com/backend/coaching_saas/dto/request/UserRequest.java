package com.backend.coaching_saas.dto.request;

import com.backend.coaching_saas.entity.Role;
import jakarta.validation.constraints.*;

public class UserRequest {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Enter a valid email!")
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;

    @NotNull(message = "Role is required!")
    private Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwor) {
        this.password = passwor;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
