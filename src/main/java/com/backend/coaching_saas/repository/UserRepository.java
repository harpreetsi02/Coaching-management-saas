package com.backend.coaching_saas.repository;

import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long>,
                JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByRole(Role role);
}
