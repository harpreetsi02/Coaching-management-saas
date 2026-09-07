package com.backend.coaching_saas.repository;

import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByTeacherId(Long teacherId, Pageable pageable);

    boolean existsByTeacherId(Long teacherId);

    @Query("""
            SELECT s 
            FROM Student s
            JOIN s.courses c
            WHERE c.id = :courseId
            """)
    List<Student> findStudentsByCourseId(Long courseId);
}
