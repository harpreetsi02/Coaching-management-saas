package com.backend.coaching_saas.repository;

import com.backend.coaching_saas.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
