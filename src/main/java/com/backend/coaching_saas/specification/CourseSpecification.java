package com.backend.coaching_saas.specification;

import com.backend.coaching_saas.entity.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

    public static Specification<Course> hasName(String name){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"

                );
    }

    public static Specification<Course> hasPrice(Double price){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("price"),
                        price
                );
    }
}
