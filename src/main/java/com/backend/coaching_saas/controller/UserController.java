package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.requestDTO.UserRegisterRequest;
import com.backend.coaching_saas.dto.responseDTO.UserResponse;
import com.backend.coaching_saas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
            @Valid @RequestBody  UserRegisterRequest request
    ) {
        UserResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
