package com.backend.coaching_saas.specification;

import com.backend.coaching_saas.entity.Batch;
import org.springframework.data.jpa.domain.Specification;

public class BatchSpecification {

    public static Specification<Batch> hasCourseId(Long courseId){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("course").get("id"),
                        courseId
                );
    }

    public static Specification<Batch> hasTeacherId(Long teacherId){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("teacher").get("id"),
                        teacherId
                );
    }
}
