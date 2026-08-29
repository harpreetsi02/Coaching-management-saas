package com.backend.coaching_saas.dto.response;

import java.util.List;

public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Long> courseIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseId) {
        this.courseIds = courseId;
    }
}
