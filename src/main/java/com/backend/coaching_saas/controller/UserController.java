package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.request.LoginRequest;
import com.backend.coaching_saas.dto.request.UserRequest;
import com.backend.coaching_saas.dto.response.LoginResponse;
import com.backend.coaching_saas.dto.response.PageResponse;
import com.backend.coaching_saas.dto.response.UserResponse;
import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import com.backend.coaching_saas.mapper.UserMapper;
import com.backend.coaching_saas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRequest request){

        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(
                UserMapper.toResponse(user)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/teachers")
    public ResponseEntity<UserResponse> createTeacher(
            @Valid @RequestBody UserRequest request
    ) {

        UserResponse response = userService.createTeacher(request);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role,
            Pageable pageable
    ) {
        Page<UserResponse> page =
                userService.getAllUsers(
                        name,
                        email,
                        role,
                        pageable
                );

        return ResponseEntity.ok(
                new PageResponse<>(page)
        );
    }
}
