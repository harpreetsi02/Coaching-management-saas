package com.backend.coaching_saas.specification;

import com.backend.coaching_saas.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasName(String name){
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + name.toLowerCase() + "%"
                    )
                );
    }

    public static Specification<Student> hasAge(Integer age){
        return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("age"),
                            age
                    )
                );
    }
}
