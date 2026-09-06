package com.backend.coaching_saas.repository;

import com.backend.coaching_saas.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByTeacherId(Long teacherId, Pageable pageable);

    boolean existsByTeacherId(Long teacherId);
}
