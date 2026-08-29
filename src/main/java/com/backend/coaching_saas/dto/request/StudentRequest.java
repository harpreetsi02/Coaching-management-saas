package com.backend.coaching_saas.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public class StudentRequest {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email(message = "Please enter a valid email!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;

    @NotNull(message = "Age is required!")
    @Min(value = 1, message = "Age must be greater than 0!")
    private Integer age;

    @NotEmpty(message = "At least one course is required!")
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
