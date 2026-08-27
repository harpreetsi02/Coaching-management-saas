package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.UserRegisterRequest;
import com.backend.coaching_saas.dto.responseDTO.UserResponse;
import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import com.backend.coaching_saas.exception.EmailAlreadyExistsException;
import com.backend.coaching_saas.mapper.UserMapper;
import com.backend.coaching_saas.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse register(UserRegisterRequest request){

        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(
                    "Email already exists:-> " + request.getEmail()
            );
        }

        User user = UserMapper.toEntity(request);

        user.setRole(Role.STUDENT);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(user);
    }
}
