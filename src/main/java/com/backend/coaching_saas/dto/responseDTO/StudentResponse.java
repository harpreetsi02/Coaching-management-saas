package com.backend.coaching_saas.dto.responseDTO;

public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private CourseSummaryResponse course;

    public StudentResponse(Long id, String name, String email, Integer age, CourseSummaryResponse course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    public CourseSummaryResponse getCourse() {
        return course;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}
