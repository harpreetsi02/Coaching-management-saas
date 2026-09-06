package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.request.LoginRequest;
import com.backend.coaching_saas.dto.request.UserRequest;
import com.backend.coaching_saas.dto.response.LoginResponse;
import com.backend.coaching_saas.dto.response.UserResponse;
import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import com.backend.coaching_saas.exception.EmailAlreadyExistsException;
import com.backend.coaching_saas.exception.InvalidCredentialException;
import com.backend.coaching_saas.mapper.UserMapper;
import com.backend.coaching_saas.repository.UserRepository;
import com.backend.coaching_saas.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse register(UserRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialException(
                                "Invalid email or password!"
                        ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialException(
                    "Invalid email or password!"
            );
        }

        String token = jwtService.generateToken(user);

        UserResponse userResponse = UserMapper.toResponse(user);

        return new LoginResponse(token, userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse createTeacher(UserRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        User teacher = UserMapper.toEntity(request);

        teacher.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        teacher.setRole(Role.TEACHER);

        User savedTeacher = userRepository.save(teacher);

        return UserMapper.toResponse(savedTeacher);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(
            String name,
            String email,
            Role role,
            Pageable pageable
    ) {

        Specification<User> specification = Specification.unrestricted();

        if (name != null && !name.isBlank()){
            specification = specification.and(
                    UserSpecification.hasName(name)
            );
        }

        if (email != null && !email.isBlank()){
            specification = specification.and(
                    UserSpecification.hasEmail(email)
            );
        }

        if (role != null){
            specification = specification.and(
                    UserSpecification.hasRole(role)
            );
        }

        return userRepository
                .findAll(specification, pageable)
                .map(UserMapper::toResponse);
    }
}
