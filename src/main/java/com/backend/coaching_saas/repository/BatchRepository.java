package com.backend.coaching_saas.repository;

import com.backend.coaching_saas.entity.Batch;
import com.backend.coaching_saas.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatchRepository extends
                        JpaRepository<Batch, Long>,
                                JpaSpecificationExecutor<Batch> {

    boolean existsByIdAndStudentsId(Long batchId, Long studentId);

    @Query("""
            SELECT s
            FROM Batch b
            JOIN b.students s
            WHERE b.id = :batchId
            """)
    List<Student> findStudentsByBatchId(@Param("batchId") Long batchId);

}
