package com.backend.coaching_saas.specification;

import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasName(String name){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<User> hasEmail(String email){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("email")),
                        email.toLowerCase()
                );
    }

    public static Specification<User> hasRole(Role role){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("role"),
                        role
                );
    }
}
