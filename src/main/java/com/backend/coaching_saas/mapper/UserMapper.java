package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.requestDTO.UserRegisterRequest;
import com.backend.coaching_saas.dto.responseDTO.UserResponse;
import com.backend.coaching_saas.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user){

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }

    public static User toEntity(UserRegisterRequest request){

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }
}
