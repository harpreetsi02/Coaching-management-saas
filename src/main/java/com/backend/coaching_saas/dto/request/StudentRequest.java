package com.backend.coaching_saas.dto.request;

import java.util.List;

public class StudentRequest {
    private String name;
    private String email;
    private String password;
    private Integer age;
    private List<Long> courseIds;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
