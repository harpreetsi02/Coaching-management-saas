package com.backend.coaching_saas.repository;

import com.backend.coaching_saas.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"course"})
    List<Student> findAll();
}